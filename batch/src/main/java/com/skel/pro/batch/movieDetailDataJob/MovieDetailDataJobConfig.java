package com.skel.pro.batch.movieDetailDataJob;

import com.skel.pro.batch.movieDetailDataJob.task.MovieDetailDataTask;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.ExecutorService;

@Configuration
@RequiredArgsConstructor
public class MovieDetailDataJobConfig {

    private final String JOB_NAME = "movieDetailData";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final JdbcTemplate jdbcTemplate;
    private final CloseableHttpClient httpClient;
    private final ExecutorService multiThPool;

    @Bean
    public Job movieDetailDataJob(){
        return jobBuilderFactory.get(JOB_NAME+"Job")
                .start(movieDetailDataTaskStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step movieDetailDataTaskStep(){
        return stepBuilderFactory.get(JOB_NAME+"TaskStep")
                .tasklet(new MovieDetailDataTask(httpClient, multiThPool, jdbcTemplate))
                .build();
    }

}
