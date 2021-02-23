package com.skel.pro.util.crw.du;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.skel.pro.util.crw.CrwParser;
import com.skel.pro.util.crw.CrwVo;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Data
public class DuMovieDetailParser implements CrwParser {

    private final CloseableHttpClient client;

    @Override
    public void read(CrwVo vo) throws IOException {
        String url = (String) vo.getInput();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(":authority", "movie.daum.net");
        httpGet.setHeader(":method", "GET");
        httpGet.setHeader(":scheme", "https");
        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("accept-encoding", "gzip, deflate, br");
        httpGet.setHeader("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        httpGet.setHeader("cache-control", "max-age=0");
        httpGet.setHeader("sec-fetch-dest", "document");
        httpGet.setHeader("sec-fetch-mode", "navigate");
        httpGet.setHeader("sec-fetch-site", "same-site");
        httpGet.setHeader("sec-fetch-user", "?1");
        httpGet.setHeader("upgrade-insecure-requests", "1");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36");

        HttpResponse response = client.execute(httpGet);
        String html = EntityUtils.toString(response.getEntity());

        Document doc = Jsoup.parse(html);

        Map<String, Object> movie = Maps.newHashMap();
        doc.getElementsByClass("box_basic").stream().forEach(a->{
            movie.put("title", a.getElementsByClass("detail_tit_fixed").text());
            movie.put("banner", a.getElementsByAttribute("style").attr("style"));

            a.getElementsByClass("list_cont").stream().forEach(b->{
                if(b.getElementsByTag("dt").text().equals("등급")) {
                    String level = b.getElementsByTag("dd").text().replaceAll("[^0-9]", "");
                    movie.put("level", level.equals("")?"0":level);
                }
                if(b.getElementsByTag("dt").text().equals("개봉")) movie.put("openDate", b.getElementsByTag("dd").text().replaceAll("[^0-9]", ""));
                if(b.getElementsByTag("dt").text().equals("장르")) movie.put("genre", b.getElementsByTag("dd").text());
                if(b.getElementsByTag("dt").text().equals("국가")) movie.put("country", b.getElementsByTag("dd").text());
                if(b.getElementsByTag("dt").text().equals("러닝타임")) movie.put("runtime", b.getElementsByTag("dd").text().replaceAll("[^0-9]", ""));
                if(b.getElementsByTag("dt").text().equals("평점")) movie.put("point", b.getElementsByTag("dd").text());
                if(b.getElementsByTag("dt").text().equals("누적관객")) movie.put("count", b.getElementsByTag("dd").text().replaceAll("[^0-9]", ""));
                if(b.getElementsByTag("dt").text().equals("수상내역")) movie.put("award", b.getElementsByTag("dd").text());
            });

        });

        List<Map<String, String>> actors = Lists.newArrayList();
        doc.select(".list_crewall > li").forEach(a->{
            Map<String, String> actor = Maps.newHashMap();
            actor.put("img", a.getElementsByTag("img").attr("src"));
            actor.put("role", a.getElementsByClass("txt_info").text());
            actor.put("name", a.getElementsByTag("strong").text());
            actors.add(actor);
        });

        movie.put("actors", actors);

        String[] split = doc.getElementsByClass("desc_cont").toString().split("making_note");
        Document storyDoc = Jsoup.parse(split[0]);
        movie.put("story", storyDoc.text());
        vo.setOutput(movie);
    }

}
