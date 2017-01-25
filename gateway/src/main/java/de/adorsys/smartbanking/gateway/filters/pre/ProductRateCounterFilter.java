package de.adorsys.smartbanking.gateway.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Florian Hirsch
 */
public class ProductRateCounterFilter extends ZuulFilter {

    private static class RateLimiter {

        private final long maxRequests;
        private final double rate;
        private long lastCall = 0L; // using nano seconds
        private double requestsRemaining = 0L;

        RateLimiter(long maxRequestsPerSecond) {
            this.maxRequests = maxRequestsPerSecond;
            this.rate = (double) maxRequestsPerSecond / TimeUnit.SECONDS.toNanos(1);
        }

        boolean isRateExceeded() {
            long now = System.nanoTime();
            long delta = now - lastCall;
            lastCall = now;
            requestsRemaining += delta * rate;
            if (requestsRemaining > maxRequests) {
                requestsRemaining = maxRequests;
            }
            if (requestsRemaining < 1) {
                return true;
            }
            requestsRemaining -= 1;
            return false;
        }

    }

    private static final Logger LOG = LoggerFactory.getLogger(ProductRateCounterFilter.class);
    private final RateLimiter rateLimiter = new RateLimiter(50);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        String requestURI = RequestContext.getCurrentContext().getRequest().getRequestURI();
        return requestURI.startsWith("/product") && !requestURI.contains("fallback");
    }

    @Override
    public Object run() {
        if (rateLimiter.isRateExceeded()) {
            LOG.info("RateLimit of {} requests per second exceeded", rateLimiter.maxRequests);
            RequestContext.getCurrentContext().put("PRODUCT_RATE_LIMIT_EXCEEDED", true);
        }
        return null;
    }

}
