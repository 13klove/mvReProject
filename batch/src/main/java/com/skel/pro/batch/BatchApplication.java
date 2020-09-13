package com.skel.pro.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static org.springframework.boot.SpringApplication.*;


@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {

    public static void main(String[] args) { run(BatchApplication.class, args); }

}
