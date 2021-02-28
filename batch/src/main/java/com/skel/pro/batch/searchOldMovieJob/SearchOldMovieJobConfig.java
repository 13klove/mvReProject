package com.skel.pro.batch.searchOldMovieJob;

import com.skel.pro.batch.domain.actor.repository.ActorJpaRepository;
import com.skel.pro.batch.domain.country.repository.CountryJpaRepository;
import com.skel.pro.batch.domain.genre.repository.GenreJpaRepository;
import com.skel.pro.batch.searchOldMovieJob.processor.SearchOldMoviesProcessor;
import com.skel.pro.common.country.status.CountryCodeDiv;
import com.skel.pro.common.genre.status.GenreCodeDiv;
import com.skel.pro.common.movie.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SearchOldMovieJobConfig {

    private final String JOB_NAME = "searchOldMovie";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final CloseableHttpClient httpClient;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    private final ActorJpaRepository actorJpaRepository;
    private final GenreJpaRepository genreJpaRepository;
    private final CountryJpaRepository countryJpaRepository;

    private final CountryCodeDiv countryCodeDiv = new CountryCodeDiv();
    private final GenreCodeDiv genreCodeDiv = new GenreCodeDiv();

    private final int MOVIE_READ_CHUNK = 1000;

    @Bean
    public Job searchOldMovieJob() throws Exception {
        return jobBuilderFactory.get(JOB_NAME+"Job")
                .start(searchOldMoviesInsertStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step searchOldMoviesInsertStep() throws Exception {
        return stepBuilderFactory.get(JOB_NAME+"Step")
                .<String, Movie>chunk(MOVIE_READ_CHUNK)
                .reader(searchOldMoviesReader())
                .processor(new SearchOldMoviesProcessor(httpClient, actorJpaRepository, genreJpaRepository, countryJpaRepository, countryCodeDiv, genreCodeDiv))
                .writer(searchOldMoviesWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<String> searchOldMoviesReader() throws Exception {
        return new JdbcPagingItemReaderBuilder<String>()
                .pageSize(MOVIE_READ_CHUNK)
                .fetchSize(MOVIE_READ_CHUNK)
                .dataSource(dataSource)
                .rowMapper(new SingleColumnRowMapper<>(String.class))
                .queryProvider(createQuery())
                .name("searchOldMoviesReader")
                .build();
    }

    @Bean
    public PagingQueryProvider createQuery() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("idtemp_detail_link");
        queryProvider.setFromClause("temp_detail_link");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("idtemp_detail_link", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);
        return queryProvider.getObject();
    }

    @Bean
    public JpaItemWriter<Movie> searchOldMoviesWriter(){
        JpaItemWriter<Movie> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


}
