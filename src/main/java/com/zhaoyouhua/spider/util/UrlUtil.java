package com.zhaoyouhua.spider.util;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtil {

    //.com;.cn;.xin;.net;.top;.在线;.xyz;.wang;.shop;.site;.club;.cc;.fun;.online;.biz;
    //.red;.link;.ltd;.mobi;.info;.org;.com.cn;.net.cn;.org.cn;.gov.cn;.name;.vip;.pro;
    //.work;.tv;.kim;.group;.tech;.store;.ren;.ink;.中文网;.我爱你;.中国;.网址;.网店;.公司;.网络;.集团;;
    public static String getDomainName(String url) {
        String d = "";
        try {
            d = getDomainNamePri(url);
            String[] arr = d.split("\\.");
            d = d.trim();
            if (d.toLowerCase().endsWith(".com.cn") ||
                    d.toLowerCase().endsWith(".net.cn") ||
                    d.toLowerCase().endsWith(".org.cn") ||
                    d.toLowerCase().endsWith(".gov.cn")
            ) {
                //目前常见域名里面,超过1段的,域名加长取全
                return arr[arr.length - 3] + "." + arr[arr.length - 2] + "." + arr[arr.length - 1];
            } else {
                return arr[arr.length - 2] + "." + arr[arr.length - 1];
            }
        } catch (Exception e) {
            return d;
        }

    }

    public static String getDomainNamePri(String url) throws URISyntaxException {
        url = url.trim();
        if (!url.toLowerCase().startsWith("http")) {
            url = "http://" + url;
        }
        url = url.replaceAll("(http\\:\\/\\/.*?\\/).*", "$1");
        URI uri = new URI(url);
        String domain = uri.getHost();
        if (domain == null) {
            return url.replace("http://", "").replaceAll("\\/", "");
        }
        try {
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (Exception e) {
            return url.replace("http://", "").replaceAll("\\/", "");
        }
    }
}
