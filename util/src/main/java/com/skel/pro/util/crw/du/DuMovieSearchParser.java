package com.skel.pro.util.crw.du;

import com.skel.pro.util.crw.CrwParser;
import com.skel.pro.util.crw.CrwVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Data
@Slf4j
public class DuMovieSearchParser implements CrwParser {

    private final CloseableHttpClient client;
    private final String host = "https://search.daum.net/search?w=tot&DA=YZR&q=";

    @Override
    public void read(CrwVo vo) throws IOException {
        String kwd = (String) vo.getInput();
        HttpGet httpGet = new HttpGet(host+kwd.replaceAll(" ", ""));
        httpGet.setHeader(":authority", "search.daum.net");
        httpGet.setHeader(":method", "GET");
        httpGet.setHeader(":scheme", "https");
        httpGet.setHeader(":path", "/search");
        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("accept-encoding", "gzip, deflate");
        httpGet.setHeader("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        httpGet.setHeader("cache-control", "max-age=0");
        httpGet.setHeader("referer", "https://www.daum.net/");
        httpGet.setHeader("sec-fetch-dest", "document");
        httpGet.setHeader("sec-fetch-mode", "navigate");
        httpGet.setHeader("sec-fetch-site", "same-site");
        httpGet.setHeader("sec-fetch-user", "?1");
        httpGet.setHeader("upgrade-insecure-requests", "1");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36");

        HttpResponse execute = client.execute(httpGet);
        String text = EntityUtils.toString(execute.getEntity());

        Document doc = Jsoup.parse(text);
        try{
            String detailLink = doc.getElementById("movieTitle").getElementsByTag("a").attr("href");
            vo.setOutput(detailLink);
        }catch (NullPointerException e){
            log.warn("{} 영화는 검색 url이 없습니다.", kwd);
        }
    }

}
