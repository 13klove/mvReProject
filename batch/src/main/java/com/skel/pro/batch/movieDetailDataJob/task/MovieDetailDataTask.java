package com.skel.pro.batch.movieDetailDataJob.task;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.skel.pro.batch.movieDetailDataJob.dto.Actor;
import com.skel.pro.batch.movieDetailDataJob.service.MovieDetailLinkService;
import com.skel.pro.batch.movieDetailDataJob.service.MovieDetailService;
import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.nv.NvMoviesParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class MovieDetailDataTask implements Tasklet {

    private final CloseableHttpClient client;
    private final ExecutorService multiThPool;
    private final JdbcTemplate jdbcTemplate;

    private final int BATCH_SIZE = 1000;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        CrwVo crwVo = CrwVo.builder().build();
        NvMoviesParser nvMoviesParser = new NvMoviesParser(client);
        nvMoviesParser.read(crwVo);

        List<String> movieNm = (List<String>) crwVo.getOutput();
        log.info("영화 이름: {}", movieNm.size());

        List<String> detailLinks = Lists.newArrayList();
        for(String movieName : movieNm){
            detailLinks.add(multiThPool.submit(new MovieDetailLinkService(movieName, client)).get());
        }
        log.info("상세 url: {}", detailLinks.size());

        List<Map<String, Object>> detailDatas = Lists.newArrayList();
        for(String url : detailLinks){
            if(url!=null) detailDatas.add(multiThPool.submit(new MovieDetailService(url, client)).get());
        }
        log.info("상세 데이터: {}", detailDatas.size());

        Set<String> genres = Sets.newHashSet();
        Set<String> country = Sets.newHashSet();
        Set<Actor> actors = Sets.newHashSet();

        for(Map<String, Object> movie : detailDatas){
            String[] webGenres = (String[]) MapUtils.getObject(movie, "genre");
            if(webGenres!=null) genres.addAll(Arrays.stream(webGenres).collect(Collectors.toList()));

            String[] webCountry = (String[]) MapUtils.getObject(movie, "country");
            if(webCountry!=null) country.addAll(Arrays.stream(webCountry).collect(Collectors.toList()));

            List<Map<String, String>> webActors = (List<Map<String, String>>) MapUtils.getObject(movie, "actors");
            if(!webActors.isEmpty()){
                List<Actor> collect = webActors.stream().map(a -> new Actor(MapUtils.getString(a, "img"), MapUtils.getString(a, "role"), MapUtils.getString(a, "name"))).collect(Collectors.toList());
                actors.addAll(collect);
            }
        }

        jdbcTemplate.batchUpdate("INSERT INTO genre(genre) VALUES (?)", genres, BATCH_SIZE, new ParameterizedPreparedStatementSetter<String>(){
            @Override
            public void setValues(PreparedStatement preparedStatement, String s) throws SQLException {
                preparedStatement.setString(1, s);
            }
        });

        jdbcTemplate.batchUpdate("INSERT INTO country(`country`) VALUES (?)", country, BATCH_SIZE, new ParameterizedPreparedStatementSetter<String>() {
            @Override
            public void setValues(PreparedStatement preparedStatement, String s) throws SQLException {
                preparedStatement.setString(1, s);
            }
        });

        jdbcTemplate.batchUpdate("INSERT INTO actor (name,role,actor_img) VALUES (?,?,?)", actors, BATCH_SIZE, new ParameterizedPreparedStatementSetter<Actor>() {
            @Override
            public void setValues(PreparedStatement preparedStatement, Actor actor) throws SQLException {
                preparedStatement.setString(1, actor.getName());
                preparedStatement.setString(2, actor.getRole().toString());
                preparedStatement.setString(3, actor.getActorImg());
            }
        });

        return RepeatStatus.FINISHED;
    }

}
