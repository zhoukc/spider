package com.zhaoyouhua.spider.jsoup;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyouhua.spider.constant.ClientType;
import com.zhaoyouhua.spider.constant.SearchEngine;
import com.zhaoyouhua.spider.constant.SearchEngineType;
import com.zhaoyouhua.spider.model.QueryItem;
import com.zhaoyouhua.spider.model.ResultRank;
import com.zhaoyouhua.spider.model.ro.SpiderInput;
import com.zhaoyouhua.spider.parse.DocParser2;
import com.zhaoyouhua.spider.selenium.SeleniumFactory;
import com.zhaoyouhua.spider.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ParseFactory {


    public ResultRank crawlerForCRM(SpiderInput input) {
        SearchEngine searchEngine = SearchEngineType.getSearchEngine(input.getEngineType());
        QueryItem queryItem = new QueryItem();
        queryItem.setSearchEngine(searchEngine);
        queryItem.setWebsite(input.getWebsite());
        queryItem.setKeyword(input.getKeyword());
        ResultRank resultRank = ResultRank.instance(queryItem);
        if (input.getEngineType() == 2) {
            queryItem.setClientType(ClientType.MOBILE);
        }
        handle(input.isFirst(), input.getEngineType(), resultRank);
        return resultRank;
    }

    public void handle(boolean isFirst, int engineType, ResultRank resultRank) {
        if (isFirst) {
            switch (engineType) {
                case 1:
                    if (resultRank.getQueryItem().getWebsite().equalsIgnoreCase("house.zjk169.net")) {
                        top_level_BaiduPc(resultRank);
                    } else {
                        Document document = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), 1, false, false, null);
                        DocParser2.parseBaiDu(document, 1, resultRank);
                    }
                    break;
                case 2:
                    if (resultRank.getQueryItem().getWebsite().equalsIgnoreCase("mm.zjk169.net")) {
                        top_level_BaiduMo(resultRank);
                    } else {
                        Document document2 = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), 1, true, false, null);
                        DocParser2.parseBaiDu(document2, 1, resultRank);
                    }
                    break;
                case 4:
                    Document document3 = JsoupFactory.getSogouCrawler(resultRank.getQueryItem().getKeyword(), 1, false, null);
                    DocParser2.parseSouGou(document3, 1, resultRank);
                    break;
                case 8:
                    Document document4 = JsoupFactory.getSo360Crawler(resultRank.getQueryItem().getKeyword(), 1, false, null);
                    DocParser2.parseSO360(document4, 1, resultRank);
                    break;
            }


        } else {
//            if (engineType == 1) {
//                SeleniumFactory.getBaiduDoc(resultRank.getQueryItem().getKeyword(),resultRank,false);
//            }
            int pageNum = 1;
            do {
                switch (engineType) {
                    case 1:
                        Document document = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), pageNum, false, false, null);
                        DocParser2.parseBaiDu(document, pageNum, resultRank);
                        break;
                    case 2:
                        Document document2 = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), pageNum, true, false, null);
                        DocParser2.parseBaiDu(document2, pageNum, resultRank);
                        break;
                    case 4:
                        Document document3 = JsoupFactory.getSogouCrawler(resultRank.getQueryItem().getKeyword(), pageNum, true, null);
                        DocParser2.parseSouGou(document3, pageNum, resultRank);
                        break;
                    case 8:
                        Document document4 = JsoupFactory.getSo360Crawler(resultRank.getQueryItem().getKeyword(), pageNum, true, null);
                        DocParser2.parseSO360(document4, pageNum, resultRank);
                        break;
                }

                pageNum++;

            } while ((resultRank.getPageNum() == -1 && pageNum <= 2));

        }

    }


    private void top_level_BaiduPc(ResultRank resultRank) {

        Document document = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), 1, false, false, null);
        try {
            Elements containers = document.getElementsByClass("c-container");//获取每个条目
            for (int position = 0; position < containers.size(); position++) {
                try {
                    Element element = containers.get(position);
                    Elements a = element.getElementsByClass("c-showurl");
                    String site = a.get(0).ownText();
                    String domainName = UrlUtil.getDomainName(site);
                    if (domainName.equalsIgnoreCase("house.zjk169.net")) {
                        String href = a.get(0).attr("href");
                        Document doc = Jsoup.connect(href).get();
                        if (doc.baseUri().equalsIgnoreCase("http://house.zjk169.net/")) {
                            log.info(resultRank.getQueryItem().getKeyword() + ";第" + 1 + "页,排名第" + (position + 1) + ";URL:" + domainName);
                            resultRank.setPageNum(1);
                            resultRank.setPosition((position + 1));
                            DocParser2.saveSnapshot(element, 1, site, resultRank);
                        }
                    }
                } catch (Exception e) {
                    log.error("百度-没有条目");
                }
            }

        } catch (Exception e) {
            log.error("没有链接", e);
        }


    }

    private void top_level_BaiduMo(ResultRank resultRank) {
        Document document = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), 1, true, false, null);

        try {
            Element results = document.getElementById("results");
            Elements resultContent = results.getElementsByClass("c-result");//获取每个条目 c-result
            for (int i = 0; i < resultContent.size(); i++) {

                try {
                    Element element = resultContent.get(i);
                    String data_log = element.attr("data-log");
                    log.info("data_log:" + data_log);
                    JSONObject jsStr = JSONObject.parseObject(data_log);

                    String site = jsStr.getString("mu");
                    String domainName = UrlUtil.getDomainName(site);
                    System.out.println(domainName + "第" + jsStr.getString("order") + "个位置");
                    if ("http://mm.zjk169.net/".equalsIgnoreCase(site)) {
                        log.info(resultRank.getQueryItem().getKeyword() + ";第" + 1 + "页,排名第" + jsStr.getString("order") + ";URL:" + domainName);
                        resultRank.setPageNum(1);
                        resultRank.setPosition(Integer.parseInt(jsStr.getString("order")));
                        DocParser2.saveSnapshot(element, (i + 1), site, resultRank);
                        break;
                    }
                } catch (Exception e) {
                    log.error("百度移动-没有条目");
                }

            }


        } catch (Exception e) {
            log.error("没有链接", e);
        }

    }


    public static void main(String[] args) throws Exception {
        Document document = JsoupFactory.getBaiDuCrawler("西安蟑螂药", 1, true, false, null);
        try {
            Element results = document.getElementById("results");
            Elements resultContent = results.getElementsByClass("c-result");//获取每个条目 c-result
            for (int i = 0; i < resultContent.size(); i++) {

                try {
                    Element element = resultContent.get(i);
                    String data_log = element.attr("data-log");
                    log.info("data_log:" + data_log);
                    JSONObject jsStr = JSONObject.parseObject(data_log);

                    String domainName = UrlUtil.getDomainName(jsStr.getString("mu"));
                    System.out.println(domainName + "第" + jsStr.getString("order") + "个位置");
//                    if (UrlUtil.getDomainName(resultRank.getQueryItem().getWebsite()).equalsIgnoreCase(domainName)) {
//                        log.info(resultRank.getQueryItem().getKeyword() + ";第" + pageNum + "页,排名第" + i + ";URL:" + domainName);
//                        resultRank.setPageNum(pageNum);
//                        resultRank.setPosition((i + 1));
//                        saveSnapshot(element, (i + 1), site, resultRank); c-gap-bottom-small
//                        break;
//                    }
                } catch (Exception e) {
                    log.error("百度移动-没有条目");
                }

            }

        } catch (Exception e) {
            log.error("没有链接", e);
        }
//        Document doc = Jsoup.connect("http://www.baidu.com/link?url=ttHON2x-JkApAJGJpIy8Abz3SRmMHwpW4ukbiefVlLe").get();
//        System.out.println(doc.baseUri());
    }

    private static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(LocalDateTime.now());
    }

    private static String getRealURL(String u) throws Exception {
        if (!u.toLowerCase().startsWith("http")) {
            return u;
        }
        int r = 10;
        Exception lstException = null;
        while (r-- > 0) {
            try {
                Connection.Response resp = Jsoup.connect(u).followRedirects(false).execute();
                if (resp.hasHeader("location")) {
                    System.out.println("Is URL going to redirect : " + resp.hasHeader("location"));
                    System.out.println("Target : " + resp.header("location"));
                    System.out.println("Target2 : " + resp.header("Referer"));
                    u = resp.header("location");
                }
                return u;
            } catch (Exception e) {
                lstException = e;
            }
        }
        if (lstException != null) {
            System.out.println("[ ***** ]获取getRealURL(" + u + ")失败！！" + lstException.getMessage());
            throw lstException;
        }
        return null;
    }
}