package com.skel.pro.batch.movieDetailDataJob.service;

import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.du.DuMovieSearchParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
public class MovieDetailLinkService implements Callable<String> {

    private final String detailLink;
    private final CloseableHttpClient httpClient;

    @Override
    public String call() throws Exception {
        CrwVo searchCrwVo = CrwVo.builder().input(detailLink).build();
        DuMovieSearchParser duMovieSearchParser = new DuMovieSearchParser(httpClient);
        try{
            duMovieSearchParser.read(searchCrwVo);
        }catch (Exception e){
            log.warn("{} 영화는 인코딩에 이슈가 있습니다.", detailLink);
        }
        return (String) searchCrwVo.getOutput();
    }

}
