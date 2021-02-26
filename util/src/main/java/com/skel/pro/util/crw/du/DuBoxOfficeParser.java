package com.skel.pro.util.crw.du;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class DuBoxOfficeParser implements CrwParser {

    private final CloseableHttpClient client;
    private final String host = "https://movie.daum.net/ranking/boxoffice/weekly";

    @Override
    public void read(CrwVo vo) throws IOException {
        HttpGet httpGet = new HttpGet(host);

        httpGet.setHeader(":authority", "movie.daum.net");
        httpGet.setHeader(":method", "GET");
        httpGet.setHeader(":path", "/ranking/boxoffice/weekly");
        httpGet.setHeader(":scheme", "https");
        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("accept-encoding", "gzip, deflate, br");
        httpGet.setHeader("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        httpGet.setHeader("cache-control", "no-cache");
        httpGet.setHeader("pragma", "no-cache");
        httpGet.setHeader("referer", "https://movie.daum.net/ranking/boxoffice/weekly");
        httpGet.setHeader("sec-fetch-dest", "document");
        httpGet.setHeader("sec-fetch-mode", "navigate");
        httpGet.setHeader("sec-fetch-site", "same-site");
        httpGet.setHeader("sec-fetch-user", "?1");
        httpGet.setHeader("upgrade-insecure-requests", "1");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36   ");

        HttpResponse execute = client.execute(httpGet);
        String response = EntityUtils.toString(execute.getEntity());

        Document doc = Jsoup.parse(response);

        List<Map<String, String>> result = Lists.newArrayList();
        for(Element element: doc.getElementsByClass("list_movieranking")){
            element.getElementsByTag("li").stream().forEach(a-> {
                Map<String, String> data = Maps.newHashMap();

                data.put("img", a.getElementsByTag("img").attr("src"));
                data.put("rank", a.getElementsByClass("rank_num").text());
                data.put("story", a.getElementsByClass("link_story").text());
                data.put("title", a.getElementsByClass("link_txt").text());

                for(Element subElement: a.getElementsByClass("info_txt")){
                    String value = subElement.text().replaceAll("[^0-9]","");
                    if (subElement.getElementsByClass("screen_out").text().equals("관객수")) data.put("boxOffice", value);
                    else data.put("openDate", value);
                }

                result.add(data);
            });
        }

        vo.setOutput(result);
    }
}
