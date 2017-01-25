package de.adorsys.smartbanking.gateway.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Florian Hirsch
 */
public class DebugRequestFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(DebugRequestFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        LOG.info("{} {}", request.getMethod(), request.getRequestURL().toString());
        return null;
    }

}
