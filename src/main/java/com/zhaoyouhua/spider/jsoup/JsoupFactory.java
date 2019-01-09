package com.zhaoyouhua.spider.jsoup;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyouhua.spider.proxy.ProxyFactory;
import com.zhaoyouhua.spider.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class JsoupFactory {

    private static Random random = new Random();
    private static String[] uaMobile = {"Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Mobile Safari/537.36",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/546.10 (KHTML, like Gecko) Version/6.0 Mobile/7E18WD Safari/8536.25",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10A5376e",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5",
            "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53",
            "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25",
            "Mozilla/5.0 (iPad; CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3",
            "Mozilla/5.0 (iPad; CPU OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5"};
    private static String[] uaPc = {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.277.400 QQBrowser/9.4.7658.400",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 UBrowser/5.6.12150.8 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0",
            "Mozilla/4.0 (compatible;MSIE7.0;WindowsNT5.1;Maxthon2.0)", "Mozilla/4.0 (compatible;MSIE7.0;WindowsNT5.1;TencentTraveler4.0)",
            "Mozilla/4.0 (compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)",
            "Mozilla/4.0 (compatible;MSIE7.0;WindowsNT5.1;360SE)", "Mozilla/4.0 (compatible;MSIE7.0;WindowsNT5.1)",
            "Mozilla/4.0 (compatible;MSIE7.0;WindowsNT5.1;AvantBrowser)", "Opera/9.80 (WindowsNT6.1;U;en)Presto/2.8.131Version/11.11",
            "Opera/9.80 (Macintosh;IntelMacOSX10.6.8;U;en)Presto/2.8.131Version/11.11"
    };


    public static Document getBaiDuCrawler(String keyword, int pageNum, boolean isMobile, boolean isProxy, Map<String, String> headers) {
        String site = "";
        String userAgent = "";
        if (headers == null) {
            headers = new HashMap<>();
        }
        if (isMobile) {
            site = createBaiDuMobileUrl(keyword, pageNum);
            userAgent = uaMobile[random.nextInt(uaMobile.length)];
            log.info("UserAgent:" + userAgent);
        } else {
            site = createBaiDuPcUrl(keyword, pageNum);
            userAgent = uaPc[random.nextInt(uaPc.length)];
            log.info("UserAgent:" + userAgent);
            headers.put("Host", "www.baidu.com");
            headers.put("Referer", "https://www.baidu.com/");

        }
        return getDoc(site, userAgent, isProxy, headers);
    }


    public static Document getSogouCrawler(String keyword, int pageNum, boolean isProxy, Map<String, String> headers) {
        String site = createSoGouUrl(keyword, pageNum);
        String userAgent = uaPc[random.nextInt(uaPc.length)];
        log.info("UserAgent:" + userAgent);
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Host", "www.sogou.com");
        headers.put("Referer", "https://www.sogou.com/");
        return getDoc(site, userAgent, isProxy, headers);
    }

    public static Document getSo360Crawler(String keyword, int pageNum, boolean isProxy, Map<String, String> headers) {
        String site = createSo360Url(keyword, pageNum);
        String userAgent = uaPc[random.nextInt(uaPc.length)];
        log.info("UserAgent:" + userAgent);
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Host", "www.so.com");
        headers.put("Referer", "https://www.so.com/");
        return getDoc(site, userAgent, isProxy, headers);
    }


    private static Document getDoc(String site, String userAgent, boolean isProxy, Map<String, String> headers) {
        int tryTimes = 0;
        while ((tryTimes++) < 5) {
            try {
                Connection connection = Jsoup.connect(site)
                        .ignoreHttpErrors(true)
                        .ignoreContentType(true)
                        .timeout(1000 * 30).userAgent(userAgent);
                if (headers != null) {
                    connection.headers(headers);
                }
                if (isProxy) {
                    //"180.164.24.165", 53281 "115.155.122.148", 8118 "116.7.176.75", 8118
                    JSONObject jsonObject = ProxyFactory.randomProxy();
                    System.out.println(jsonObject.toJSONString());
                    connection.proxy(jsonObject.getString("ip"), jsonObject.getIntValue("port"));
                    isProxy = false;
                }
                Document doc = connection.get();
                System.out.println(doc.baseUri());
                if (doc.baseUri().startsWith("http://www.sogou.com/antispider/")) {
                    isProxy = true;
                    throw new IOException("搜狗反爬虫机制");
                }
                return doc;
            } catch (SocketTimeoutException so) {
                log.error("读取超时,重试第" + tryTimes + "次");
                continue;
            } catch (ConnectException exception) {
                log.error("连接超时,重试第" + tryTimes + "次");
                continue;
            } catch (IOException e) {
                log.error("获取文档出错,重试第" + tryTimes + "次", e);
            }
        }
        return null;
    }


    private static String createSoGouUrl(String keyword, int pageNum) {
        int first = pageNum;
        try {
            keyword = URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("编码异常", e);
        }
        return String.format("http://www.sogou.com/web?query=%s&page=%s", keyword, first);
    }

    private static String createSo360Url(String keyword, int pageNum) {
        int first = pageNum;
        try {
            keyword = URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("编码异常", e);
        }
        return String.format("https://www.so.com/s?q=%s&pn=%s", keyword, first);
    }

    private static String createBaiDuPcUrl(String keyword, int pageNum) {
        int first = (pageNum - 1) * 10;
        try {
            keyword = URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("编码异常", e);
        }
        return String.format("http://www.baidu.com/s?wd=%s&pn=%s", keyword, first);
    }

    private static String createBaiDuMobileUrl(String keyword, int pageNum) {
        int first = (pageNum - 1) * 10;
        try {
            keyword = URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("编码异常", e);
        }
        return String.format("http://m.baidu.com/s?word=%s&pn=%s", keyword, first);
    }


    public static void main(String[] args) {

        testBadiu();

    }

    private static void testBadiu() {
        Document doc = JsoupFactory.getBaiDuCrawler("氟胶O型圈厂家", 1, false, false, null);

        Elements result = doc.getElementsByClass("c-container");
        for (int rank = 0; rank < result.size(); rank++) {

            Elements elements = result.get(rank).getElementsByClass("c-showurl");
            Element a = elements.get(0);
            System.out.println("第" + 1 + "页,第" + (rank + 1) + "个" + a.ownText());
            System.out.println(UrlUtil.getDomainName(a.ownText()));

        }
        System.err.println(result.size());
    }

    private static void testSogou() {
        Document doc = JsoupFactory.getSogouCrawler("轴流风机", 2, true, null);
        try {
            Elements results = doc.getElementsByClass("results");
            Elements result = results.get(0).getElementsByClass("rb");
            for (int rank = 0; rank < result.size(); rank++) {

                Elements elements = result.get(rank).getElementsByClass("fb");
                Element a = elements.get(0).child(0);
                System.out.println("第" + 1 + "页,第" + (rank + 1) + "个" + a.ownText());
                System.out.println(UrlUtil.getDomainName(a.ownText()));

            }
            System.err.println(result.size());
        } catch (Exception e) {
            log.error("没有链接", e);
        }

    }

}
