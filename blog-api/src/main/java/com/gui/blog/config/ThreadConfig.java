package com.gui.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // 开启多线程
public class ThreadConfig {

    @Bean("taskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 设置核心线程数
        executor.setMaxPoolSize(10); // 设置最大线程数
        executor.setQueueCapacity(25); // 设置队列容量
        executor.setKeepAliveSeconds(60); // 设置线程活跃时间（秒）
        executor.setThreadNamePrefix("GUI博客-"); // 设置线程名称前缀
        executor.setWaitForTasksToCompleteOnShutdown(true); // 等待所有任务结束后再关闭线程池
        executor.initialize(); // 初始化线程池
        return executor;
    }

}
