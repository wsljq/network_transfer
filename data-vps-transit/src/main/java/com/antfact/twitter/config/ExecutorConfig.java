package com.antfact.twitter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
public class ExecutorConfig {

    @Bean
    ThreadPoolTaskExecutor poolExecutor(@Value("${pool.queueCapacity}") final int queueCapacity,
                                            @Value("${pool.corePoolSize}") final int corePoolSize,
                                            @Value("${pool.maxPoolSize}") final int maxPoolSize,
                                            @Value("${pool.keepAliveSeconds}") final int keepAliveSeconds) {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        // 线程池所使用的缓冲队列
        poolTaskExecutor.setQueueCapacity(queueCapacity);
        // 线程池维护线程的最少数量
        poolTaskExecutor.setCorePoolSize(corePoolSize);
        // 线程池维护线程的最大数量
        poolTaskExecutor.setMaxPoolSize(maxPoolSize);
        // 线程池维护线程所允许的空闲时间
        poolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程名前缀
        poolTaskExecutor.setThreadNamePrefix("PoolExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        poolTaskExecutor.initialize();
        return poolTaskExecutor;
    }
}
