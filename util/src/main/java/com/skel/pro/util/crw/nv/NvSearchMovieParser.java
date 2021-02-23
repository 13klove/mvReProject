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
    private final String host = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EC%98%81%ED%99%94";

    @Override
    public void read(CrwVo vo) throws IOException {
        HttpGet httpGet = new HttpGet(host);
        httpGet.setHeader("authority", "search.naver.com");
//        httpGet.setHeader(":method", "GET");
//        httpGet.setHeader(":path", "/search.naver?where=nexearch&sm=tab_etc&query=%ED%98%84%EC%9E%AC%EC%83%81%EC%98%81%EC%98%81%ED%99%94");
//        httpGet.setHeader(":scheme", "https");
//        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//        httpGet.setHeader("accept-encoding", "gzip, deflate, br");
//        httpGet.setHeader("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
//        httpGet.setHeader("cache-control", "max-age=0");
//        httpGet.setHeader("cookie", "NNB=JFN3MB3FE4PV4; ASID=dc464634000001708ea01af700000058; _ga_7VKFYR6RV1=GS1.1.1598192351.1.0.1598192355.56; _fbp=fb.1.1598779522147.1505056206; _ga_4BKHBFKFK0=GS1.1.1600255475.1.1.1600255531.4; NM_THUMB_PROMOTION_BLOCK=Y; MM_NEW=1; NFS=2; MM_NOW_COACH=1; m_loc=dc3a87521af7ac8085550656fd167f30bbc367e4c7b103f301b5d3687f3c4e4fca74d1f38b5d46de2c1d2a842f0e4f671207e3757267f07d65eaea10192440b991184833f9cac50782ca4628737fbcfb; NRTK=ag#20s_gr#0_ma#-2_si#-2_en#-2_sp#-2; _ga_1BVHGNLQKG=GS1.1.1608807017.1.1.1608807035.0; _ga=GA1.2.1592814637.1592707957; NV_WETR_LAST_ACCESS_RGN_M=\"MDk2MjA2MDU=\"; NV_WETR_LOCATION_RGN_M=\"MDk2MjA2MDU=\"; nx_ssl=2; _gid=GA1.2.1937057137.1613915712");
//        httpGet.setHeader("sec-fetch-dest", "document");
//        httpGet.setHeader("sec-fetch-mode", "navigate");
//        httpGet.setHeader("sec-fetch-site", "same-site");
//        httpGet.setHeader("sec-fetch-user", "?1");
//        httpGet.setHeader("upgrade-insecure-requests", "1");
//        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36");

        HttpResponse execute = client.execute(httpGet);
        String response = EntityUtils.toString(execute.getEntity());
        System.out.println(response);
        vo.setOutput(response);
    }

}
