package com.skel.pro.batch;

import com.skel.pro.common.country.entity.Country;
import com.skel.pro.common.movie.entity.Movie;
import com.skel.pro.common.movieCountry.entity.MovieCountry;
import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.config.HttpClientFactory;
import com.skel.pro.util.crw.du.DuMovieDetailParser;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class BatchApplicationTests {

    @Test
    public void test() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        CloseableHttpClient closeableHttpClient = HttpClientFactory.httpClient(10, 3000);

        CrwVo vo = CrwVo.builder().input("https://movie.daum.net/moviedb/main?movieId=42238").build();
        DuMovieDetailParser nvSearchMovieParser = new DuMovieDetailParser(closeableHttpClient);
        nvSearchMovieParser.read(vo);

        Map<String, Object> result = (Map<String, Object>) vo.getOutput();
        System.out.println(result);
        Assertions.assertNotNull(result);

        Movie movie = Movie.createCloseMovieDetail(result);
        String[] countrs = (String[]) MapUtils.getObject(result, "country");
        for(String mCountry : countrs){
            MovieCountry country = MovieCountry.createMovieCountry(movie, Country.createCountry(mCountry));
        }

        String[] genres = (String[]) MapUtils.getObject(result, "genre");

        System.out.println(movie);
        movie.getMovieCountries().forEach(a->{
            System.out.println(a.getCountry());
        });
    }

    @Test
    public void streamDistinct(){
        String[] arr = {"a", "a", "b"};
        System.out.println(Arrays.stream(arr).collect(Collectors.toSet()));
    }

}
