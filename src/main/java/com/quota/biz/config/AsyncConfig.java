package com.quota.biz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * 自定义线程池
 */
@Configuration
public class AsyncConfig {
    @Bean("quotaExecutor")
    public ThreadPoolTaskExecutor quotaExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setCorePoolSize(15);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
