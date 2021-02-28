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

public class DuMovieSearchParserTest {

    @Test
    public void DuMovieSearchParserReadTest() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        CloseableHttpClient closeableHttpClient = HttpClientFactory.httpClient(10, 3000);

        //CrwVo vo = CrwVo.builder().input("어느 가족, 호랑이는 겁이 없지").build();
        //CrwVo vo = CrwVo.builder().input("어느가족,호랑이는겁이없지").build();
        CrwVo vo = CrwVo.builder().input("미션임파서블").build();
        DuMovieSearchParser nvSearchMovieParser = new DuMovieSearchParser(closeableHttpClient);
        nvSearchMovieParser.read(vo);

        String detailLink = (String) vo.getOutput();
        System.out.println(detailLink);
        Assertions.assertNotNull(detailLink);
    }

}
