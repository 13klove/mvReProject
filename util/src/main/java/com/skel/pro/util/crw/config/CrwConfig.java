package com.skel.pro.util.crw.config;


import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
public class CrwConfig {

    @Bean(name="redirectHttpClient")
    public CloseableHttpClient getRedirectHttpClient(){
        return HttpClientFactory.redirectHttpClient(50, 3000);
    }

    @Bean(name="httpClient")
    public CloseableHttpClient getHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return HttpClientFactory.httpClient(50, 3000);
    }

}
