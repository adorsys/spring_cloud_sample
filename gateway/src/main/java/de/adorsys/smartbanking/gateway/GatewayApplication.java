package de.adorsys.smartbanking.gateway;

import de.adorsys.smartbanking.gateway.filters.pre.DebugRequestFilter;
import de.adorsys.smartbanking.gateway.filters.pre.ProductRateCounterFilter;
import de.adorsys.smartbanking.gateway.filters.route.ProductFallbackFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public DebugRequestFilter debugRequestFilter() {
        return new DebugRequestFilter();
    }

    @Bean
    public ProductRateCounterFilter productRateCounterFilter() {
        return new ProductRateCounterFilter();
    }

    @Bean
    public ProductFallbackFilter productFallbackFilter() {
        return new ProductFallbackFilter();
    }

}
