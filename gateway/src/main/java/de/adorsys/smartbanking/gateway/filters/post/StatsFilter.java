package de.adorsys.smartbanking.gateway.filters.post;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Florian Hirsch
 */
public class StatsFilter extends ZuulFilter {

  private static final Logger LOG = LoggerFactory.getLogger(StatsFilter.class);

  private final Counter counter = new Counter();

  @Override
  public String filterType() {
    return "post";
  }

  @Override
  public int filterOrder() {
    return 1000;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    counter.inc(RequestContext.getCurrentContext().getRouteHost().getPath(), RequestContext.getCurrentContext().getResponseStatusCode());
    LOG.info("RequestStatistics: {}", counter);
    return null;
  }

  private static class Counter {

    Map<String, Map<Integer, Integer>> stats = new HashMap<>();

    void inc(String path, int status) {
      String serviceId = path.replaceAll("/", "");
      if (!stats.containsKey(serviceId)) {
        stats.put(serviceId, new HashMap<>());
      }
      if (!stats.get(serviceId).containsKey(status)) {
        stats.get(serviceId).put(status, 0);
      }
      stats.get(serviceId).put(status, stats.get(serviceId).get(status) + 1);
    }

    @Override
    public String toString() {
      return stats.toString();
    }
  }

}
