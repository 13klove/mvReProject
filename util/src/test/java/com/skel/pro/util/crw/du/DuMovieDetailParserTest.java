package com.skel.pro.util.crw.du;

import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.config.HttpClientFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class DuMovieDetailParserTest {

    @Test
    public void DuMovieDetailParserReadTest() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient closeableHttpClient = HttpClientFactory.httpClient(10, 3000);

        CrwVo vo = CrwVo.builder().input("https://movie.daum.net/moviedb/main?movieId=40870").build();
        DuMovieDetailParser nvSearchMovieParser = new DuMovieDetailParser(closeableHttpClient);
        nvSearchMovieParser.read(vo);

        Map<String, Object> result = (Map<String, Object>) vo.getOutput();
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

}
