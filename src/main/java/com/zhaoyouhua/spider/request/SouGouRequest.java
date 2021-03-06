package com.zhaoyouhua.spider.request;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import okhttp3.Request;

import java.net.URLEncoder;

public class SouGouRequest extends OkHttpRequester {


    @Override
    public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
        // 这里使用的是OkHttp中的Request.Builder
        // 可以参考OkHttp的文档来修改请求头
        Request.Builder requestBuilder = super.createRequestBuilder(crawlDatum);
//        requestBuilder.addHeader("Accept-Encoding", "gzip, deflate, br")
//                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
//                .addHeader("Cache-Control", "max-age=0")
//                .addHeader("Connection", "keep-alive");
        requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");
//        requestBuilder.addHeader("Host", "www.sogou.com");
//        requestBuilder.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        return requestBuilder;
    }


    public String createSoGouUrl(String keyword, int pageNum) throws Exception {
        int first = pageNum;
        keyword = URLEncoder.encode(keyword, "utf-8");
        return String.format("http://www.sogou.com/web?query=%s&page=%s", keyword, first);
    }

}
