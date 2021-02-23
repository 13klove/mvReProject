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
import java.util.List;
import java.util.Map;

public class DuBoxOfficeParserTest {

    @Test
    void DuBoxOfficeParserReadTest() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient closeableHttpClient = HttpClientFactory.httpClient(10, 3000);

        CrwVo vo = CrwVo.builder().build();
        DuBoxOfficeParser nvSearchMovieParser = new DuBoxOfficeParser(closeableHttpClient);
        nvSearchMovieParser.read(vo);

        List<Map<String, String>> result = (List<Map<String, String>>) vo.getOutput();
        result.forEach(System.out::println);
        Assertions.assertTrue(result.size()>0);
    }

}
