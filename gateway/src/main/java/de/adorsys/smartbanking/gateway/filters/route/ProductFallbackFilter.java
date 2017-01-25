package de.adorsys.smartbanking.gateway.filters.route;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Florian Hirsch
 */
public class ProductFallbackFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(ProductFallbackFilter.class);

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getBoolean("PRODUCT_RATE_LIMIT_EXCEEDED", false);
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            URI requestUri = new URI(ctx.getRequest().getRequestURL().toString());
            URI fallback = requestUri.resolve("fallback");
            LOG.info("Redirecting to fallback {}", fallback);
            ctx.setRouteHost(fallback.toURL());
        } catch (MalformedURLException | URISyntaxException ex) {
            throw new IllegalArgumentException("Could not create product fallback uri", ex);
        }
        return null;
    }

}
