package com.skel.pro.batch.searchOldMovieJob;

import com.skel.pro.batch.searchOldMovieJob.dataSet.OldMovieDataSet;
import com.skel.pro.batch.searchOldMovieJob.processor.SearchOldMoviesProcessor;
import com.skel.pro.batch.searchOldMovieJob.task.SearchOldMoviesTask;
import com.skel.pro.common.movie.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SearchOldMovieJobConfig {

    private final String JOB_NAME = "searchOldMovie";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final CloseableHttpClient httpClient;
    private final OldMovieDataSet oldMovieDataSet = new OldMovieDataSet();

    private final int MOVIE_READ_CHUNK = 100;

    @Bean
    public Job searchOldMovieJob(){
        return jobBuilderFactory.get(JOB_NAME+"Job")
                .start(searchOldMoviesTaskStep())
                .next(searchOldMoviesInsertStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step searchOldMoviesTaskStep(){
        return stepBuilderFactory.get(JOB_NAME+"TaskStep")
                .tasklet(new SearchOldMoviesTask(httpClient, oldMovieDataSet))
                .build();
    }

    @Bean
    @JobScope
    public Step searchOldMoviesInsertStep(){
        return stepBuilderFactory.get(JOB_NAME+"Step")
                .<String, Movie>chunk(MOVIE_READ_CHUNK)
                .reader(new ListItemReader<String>(oldMovieDataSet.getOldMovies()))
                .processor(new SearchOldMoviesProcessor(httpClient))
                .writer(null)
                .build();
    }



}
