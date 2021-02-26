package com.skel.pro.batch.movieDetailDataJob.service;

import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.du.DuMovieDetailParser;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class MovieDetailService implements Callable<Map<String, Object>> {

    private final String url;
    private final CloseableHttpClient client;

    @Override
    public Map<String, Object> call() throws Exception {
        CrwVo detailCrwVo = CrwVo.builder().input(url).build();
        DuMovieDetailParser duMovieDetailParser = new DuMovieDetailParser(client);
        duMovieDetailParser.read(detailCrwVo);

        return (Map<String, Object>) detailCrwVo.getOutput();
    }

}
