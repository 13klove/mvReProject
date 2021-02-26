package com.skel.pro.batch.searchOldMovieJob.processor;

import com.skel.pro.common.movie.entity.Movie;
import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.du.DuMovieDetailParser;
import com.skel.pro.util.crw.du.DuMovieSearchParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.batch.item.ItemProcessor;

import java.util.Map;

@RequiredArgsConstructor
public class SearchOldMoviesProcessor implements ItemProcessor<String, Movie> {

    private final CloseableHttpClient httpClient;

    @Override
    public Movie process(String s) throws Exception {
        CrwVo searchCrwVo = CrwVo.builder().input(s).build();
        DuMovieSearchParser duMovieSearchParser = new DuMovieSearchParser(httpClient);
        duMovieSearchParser.read(searchCrwVo);

        String link = (String) searchCrwVo.getOutput();
        if(!StringUtils.isNotBlank(link)){
            CrwVo detailCrwVo = CrwVo.builder().input(link).build();
            DuMovieDetailParser duMovieDetailParser = new DuMovieDetailParser(httpClient);
            duMovieDetailParser.read(detailCrwVo);

            Map<String, Object> movie = (Map<String, Object>) detailCrwVo.getOutput();
            if(movie!=null){
                return null;
            }
        }

        return Movie.createMovieByTitle(s);
    }

}
