package com.zhaoyouhua.spider;

import com.zhaoyouhua.spider.jobs.OffLineCrawlJob;
import com.zhaoyouhua.spider.jobs.OnLineCrawlJob;
import com.zhaoyouhua.spider.jsoup.JsoupFactory;
import com.zhaoyouhua.spider.parse.DocParser2;
import com.zhaoyouhua.spider.util.UrlUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@ActiveProfiles("prod")
@SpringBootTest
public class SpiderApplicationTests {

//    @Autowired
//    private OnLineCrawlJob onLineCrawlJob;
//    @Autowired
//    private OffLineCrawlJob offLineCrawlJob;
//
//    @Test
//    public void contextLoads() {
//        onLineCrawlJob.getCrawlData();
//
//    }
//
//    @Test
//    public void  offLineCrawlJobTest()throws  Exception{
//         offLineCrawlJob.getCrawlData();
//    }


    @Test
    public void BaiDUTest() throws Exception {

        Document doc = JsoupFactory.getBaiDuCrawler("压力匹配器", 1, false, false, null);

        try {
            Element results = doc.getElementById("content_left");
            Elements resultContent = results.getElementsByClass("c-container");//获取每个条目

            for (int i = 0; i < resultContent.size(); i++) {
                try {
                    Element element = resultContent.get(i);
                    String site = element.getElementsByClass("c-showurl").get(0).ownText();
                    String domainName = UrlUtil.getDomainName(site);
                    System.out.println(domainName);
//                if (UrlUtil.getDomainName(resultRank.getQueryItem().getWebsite()).equalsIgnoreCase(domainName)) {
////                    log.info(resultRank.getQueryItem().getKeyword() + ";第" + pageNum + "页,排名第" + i + ";URL:" + domainName);
////                    resultRank.setPageNum(pageNum);
////                    resultRank.setPosition((i + 1));
////                    saveSnapshot(element, (i + 1), site, resultRank);
////                    break;
////                }
                } catch (NullPointerException e) {
                    System.out.println(e);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e);
                }

            }

        } catch (Exception e) {
            System.out.println("没有链接");

        }
    }


    @Test
    public void BaiDUMobileTest() throws Exception {

        Document doc = JsoupFactory.getBaiDuCrawler("压力匹配器", 1, true, false, null);

        try {
            Element results = doc.getElementById("results");
            Elements resultContent = results.getElementsByClass("c-result-content");//获取每个条目

            for (int i = 0; i < resultContent.size(); i++) {
                try {
                    Element element = resultContent.get(i);
                    String site = element.getElementsByClass("c-footer-showurl").get(0).ownText();
                    String domainName = UrlUtil.getDomainName(site);
                    System.out.println(domainName);
//                if (UrlUtil.getDomainName(resultRank.getQueryItem().getWebsite()).equalsIgnoreCase(domainName)) {
////                    log.info(resultRank.getQueryItem().getKeyword() + ";第" + pageNum + "页,排名第" + i + ";URL:" + domainName);
////                    resultRank.setPageNum(pageNum);
////                    resultRank.setPosition((i + 1));
////                    saveSnapshot(element, (i + 1), site, resultRank);
////                    break;
////                }
                } catch (NullPointerException e) {
                    System.out.println(e);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e);
                }

            }

        } catch (Exception e) {
            System.out.println("没有链接");

        }
    }


    @Test
    public void So360Test() throws Exception {
        Document doc = JsoupFactory.getSo360Crawler("重量复检称", 1, false, null);
        try {
            Elements results = doc.getElementsByClass("result");
            Elements resultContent = results.get(0).getElementsByClass("res-list");//获取每个条目

            for (int i = 0; i < resultContent.size(); i++) {
                try {
                    Element element = resultContent.get(i);
                    String site = element.getElementsByClass("res-linkinfo").get(0).child(0).ownText();
                    String domainName = UrlUtil.getDomainName(site);
                    System.out.println(domainName);
                } catch (NullPointerException e) {
                    System.out.println(e);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e);
                }

            }

        } catch (Exception e) {
            System.out.println("没有链接");

        }
    }
}
