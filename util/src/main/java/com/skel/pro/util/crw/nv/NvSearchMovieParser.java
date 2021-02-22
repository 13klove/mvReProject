package com.skel.pro.util.crw.nv;

import com.skel.pro.util.crw.CrwParser;
import com.skel.pro.util.crw.CrwVo;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Data
public class NvSearchMovieParser implements CrwParser {

    private final CloseableHttpClient client;
    private final String host = "https://movie.naver.com/movie/running/current.nhn";

    @Override
    public void read(CrwVo vo) throws IOException {
        HttpGet httpGet = new HttpGet(host);
        httpGet.setHeader(":authority", "movie.naver.com");
        httpGet.setHeader(":method", "GET");
        httpGet.setHeader(":path", "/movie/running/current.nhn");
        httpGet.setHeader(":scheme", "https");
        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("accept-encoding", "gzip, deflate, br");
        httpGet.setHeader("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        httpGet.setHeader("cookie", "NNB=SVKQOJKDFUVGA; nx_ssl=2; NRTK=ag#20s_gr#0_ma#-2_si#-2_en#-2_sp#-2; NDARK=Y; nid_inf=715548050; NID_AUT=SJTfXiDyHPDG197leY2SMIKITNcSMUZ9avlbJ0wOigT/AMzY2sHauqdJXqeE3W0p; NID_JKL=ejAPJF7aKmdDDH/lnrl1u0h0BvhzO+eTYJY6U25glRg=; BMR=s=1613959934799&r=https%3A%2F%2Fm.blog.naver.com%2FPostView.nhn%3FblogId%3Dkop_seop%26logNo%3D220730943499%26proxyReferer%3Dhttps%3A%252F%252Fwww.google.com%252F&r2=https%3A%2F%2Fwww.google.com%2F; page_uid=hvLNFsp0JXossCO/vtlssssst4o-164570; JSESSIONID=C1EC7D928C04C3B5FD00BB0DBA5BA83E; NM_THUMB_PROMOTION_BLOCK=Y; NID_SES=AAABm1jHV8kwklDeFSjVBb+W0g61IgaA1BE0wUit2J0fZP34hG90SMXWlp1oR8tC2CVNxeMze6OMILmyNNEqC2T32ALWl2UW1MToVbIfSSOJDTSYCkbulf4/GzZBnbksUg2BO7cQ7qVb+w4pmMnffqaglQurH2C5uxpmITED+0JV5ejSq62AqPaHMCvB2I+n+38ymG/EYUC9Cq9lX4HFmotXnsxli04uJJmJm5oZIGpUTnzGCt8JXdVVHw8a6jfSevVhWCe65+T2NavmZhrFIyGkFE76Om2e5O2zjS/6Bt+bT+Ov3V8QHQqoBlfnFpED7cFGNxJU2qvii6ls4sFIk61e7C3mbQyKvJEqfMsGXwIUkkhhWLmxklStLno+oQ0hu6ylSPXKNvU3S8B/vRpAcTsyxVGyiP3WdyZLjbwQdaKvweT4nVIZpDieAV5UTdUZcojfaMDxD9shNjzZf8jjnhC1LXPCPN4PtK+Scb+z6GuKr9h5A+g2Njxd/MGDFZZBcuESv0KCkUUQT07mO4rlDx1LynON8fB7JGfVcqMAobwjXyvX; csrf_token=35d4a4e9-7324-429f-8dd3-726ad6b56d3a");
        httpGet.setHeader("referer", "https://movie.naver.com/movie/running/current.nhn");
        httpGet.setHeader("sec-fetch-dest", "document");
        httpGet.setHeader("sec-fetch-mode", "navigate");
        httpGet.setHeader("sec-fetch-site", "same-site");
        httpGet.setHeader("sec-fetch-user", "?1");
        httpGet.setHeader("upgrade-insecure-requests", "1");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36");

        HttpResponse execute = client.execute(httpGet);
        String response = EntityUtils.toString(execute.getEntity());
        System.out.println(response);
        vo.setOutput(response);
    }

}
