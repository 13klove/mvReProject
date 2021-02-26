package com.skel.pro.batch.config;


import com.skel.pro.util.crw.config.HttpClientFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
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
