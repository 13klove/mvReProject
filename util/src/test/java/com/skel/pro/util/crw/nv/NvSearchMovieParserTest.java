package com.skel.pro.util.crw.nv;

import com.skel.pro.util.crw.CrwVo;
import com.skel.pro.util.crw.config.HttpClientFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class NvSearchMovieParserTest {

    @Test
    void NvSearchMovieParserReadTest() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient closeableHttpClient = HttpClientFactory.redirectHttpClient(1, 10000);

        CrwVo vo = CrwVo.builder().build();
        NvSearchMovieParser nvSearchMovieParser = new NvSearchMovieParser(closeableHttpClient);
        nvSearchMovieParser.read(vo);
        System.out.printf(vo.getOutput().toString());
    }

}
