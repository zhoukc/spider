package com.zhaoyouhua.spider.request;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import okhttp3.Request;

public class MyRequester extends OkHttpRequester {

    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";

    // 每次发送请求前都会执行这个方法来构建请求
    @Override
    public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
        // 这里使用的是OkHttp中的Request.Builder
        // 可以参考OkHttp的文档来修改请求头
        return super.createRequestBuilder(crawlDatum)
                .addHeader("User-Agent", userAgent);
    }
}
