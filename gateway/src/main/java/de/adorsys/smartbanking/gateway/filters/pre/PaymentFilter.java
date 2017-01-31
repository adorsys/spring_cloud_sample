package de.adorsys.smartbanking.gateway.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author Florian Hirsch
 */
public class PaymentFilter extends ZuulFilter {

  private final List<String> validTokens = Arrays.asList("4711", "0815");

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 3;
  }

  @Override
  public boolean shouldFilter() {
    return RequestContext.getCurrentContext().getRequest().getRequestURI().endsWith("/prime");
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    String apiToken = ctx.getRequest().getHeader("X-PRIME-TOKEN");
    if (!validTokens.contains(apiToken)) {
      ctx.setResponseStatusCode(402);
      ctx.setResponseBody("Payment Required");
      ctx.addZuulResponseHeader("Content-Type", "text/plain");
      ctx.setSendZuulResponse(false);
    }
    return null;
  }

}
