package com.skel.pro.util.crw.nv;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.skel.pro.util.crw.CrwParser;
import com.skel.pro.util.crw.CrwVo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Set;

/**
* @date: 20210225
* @author: hb
* @desc: 네이버에서 영화 목록을 가져온다.
 * 지금은 싱글쓰레드로 하는데 나중에는 멀티 스레드로 해도 될거 같다.
**/
@Data
@RequiredArgsConstructor
public class NvMoviesParser implements CrwParser {

    private final CloseableHttpClient client;
    private final String host = "https://serieson.naver.com/movie/categoryList.nhn?categoryCode=ALL&page=";

    @Override
    public void read(CrwVo vo) throws IOException {
        Set<String> movieName = Sets.newHashSet();
        //int size = 21949/25;
        int size = 100;
        for(int i=1; i<size; i++) {
            HttpGet httpGet = new HttpGet(host+i);

            HttpResponse response = client.execute(httpGet);
            String text = EntityUtils.toString(response.getEntity());

            Document document = Jsoup.parse(text);
            document.getElementsByClass("NPI=a:content").forEach(a -> {
                movieName.add(a.select("a").attr("title"));
            });
        }
        vo.setOutput(Lists.newArrayList(movieName));
    }

}

