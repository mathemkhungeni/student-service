package emk.student.student_service.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResilienceConfig {

  @Bean
  public CircuitBreakerRegistry circuitBreakerRegistry() {
    CircuitBreakerConfig circuitBreakerConfig =
        CircuitBreakerConfig.custom()
            .slidingWindowSize(100)
            .minimumNumberOfCalls(10)
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofSeconds(5))
            .permittedNumberOfCallsInHalfOpenState(3)
            .build();
    return CircuitBreakerRegistry.of(circuitBreakerConfig);
  }

  @Bean
  public RateLimiterRegistry rateLimiterRegistry() {
    RateLimiterConfig rateLimiterConfig =
        RateLimiterConfig.custom()
            .limitForPeriod(100)
            .limitRefreshPeriod(Duration.ofSeconds(1))
            .timeoutDuration(Duration.ZERO)
            .build();
    return RateLimiterRegistry.of(rateLimiterConfig);
  }

  @Bean
  public RetryRegistry retryRegistry() {
    RetryConfig retryConfig =
        RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofSeconds(1))
            .build();
    return RetryRegistry.of(retryConfig);
  }
} 