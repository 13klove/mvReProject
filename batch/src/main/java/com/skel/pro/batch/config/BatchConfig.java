package com.skel.pro.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan(basePackages = {"com.skel.pro"})
public class BatchConfig {

    private final int MULTI_TH_SIZE = 20;

    @Bean(name = "multiThPool")
    public ExecutorService getMultiThPool(){
        return Executors.newFixedThreadPool(MULTI_TH_SIZE);
    }

}
