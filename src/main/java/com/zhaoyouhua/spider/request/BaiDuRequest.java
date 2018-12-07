package com.zhaoyouhua.spider.request;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import okhttp3.Request;

import java.net.URLEncoder;

public class BaiDuRequest extends OkHttpRequester {

    private boolean isMobile;


    public BaiDuRequest(boolean isMobile) {
        this.isMobile = isMobile;
    }


    @Override
    public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
        // 这里使用的是OkHttp中的Request.Builder
        // 可以参考OkHttp的文档来修改请求头
        Request.Builder requestBuilder = super.createRequestBuilder(crawlDatum);
//        requestBuilder.addHeader("Accept-Encoding", "gzip, deflate")
//                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
//                .addHeader("Cache-Control", "max-age=0")
//                .addHeader("Connection", "keep-alive");
        if (isMobile) {
            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Mobile Safari/537.36");
//            requestBuilder.addHeader("Host", "m.baidu.com");
//            requestBuilder.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        } else {
            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
//            requestBuilder.addHeader("Host", "www.baidu.com");
//            requestBuilder.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        }

        return requestBuilder;
    }


    public String createBaiDuPcUrl(String keyword, int pageNum) throws Exception {
        int first = (pageNum - 1) * 10;
        keyword = URLEncoder.encode(keyword, "utf-8");
        return String.format("http://www.baidu.com/s?wd=%s&pn=%s", keyword, first);
    }

    public String createBaiDuMobileUrl(String keyword, int pageNum) throws Exception {
        int first = (pageNum - 1) * 10;
        keyword = URLEncoder.encode(keyword, "utf-8");
        return String.format("http://m.baidu.com/s?word=%s&pn=%s", keyword, first);
    }

}
