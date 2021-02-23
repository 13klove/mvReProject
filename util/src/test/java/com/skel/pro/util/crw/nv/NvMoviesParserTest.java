package com.skel.pro.util.crw.nv;

import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.config.HttpClientFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class NvMoviesParserTest {

    @Test
    public void NvMoviesParserReadTest() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient closeableHttpClient = HttpClientFactory.httpClient(10, 3000);

        CrwVo vo = CrwVo.builder().build();
        NvMoviesParser nvSearchMovieParser = new NvMoviesParser(closeableHttpClient);
        nvSearchMovieParser.read(vo);

        List<String> result = (List<String>) vo.getOutput();
        System.out.println(result);
        Assertions.assertNotNull(result);
    }

}
