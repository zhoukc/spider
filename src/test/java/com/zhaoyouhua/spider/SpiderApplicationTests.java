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
@ActiveProfiles("prod")
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
    public void sTest() throws Exception {

        Document doc = JsoupFactory.getBaiDuCrawler("西安远大教育", 1, true, false, null);

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
                }catch (IndexOutOfBoundsException e){
                    System.out.println(e);
                }

            }

        } catch (Exception e) {
            System.out.println("没有链接");

        }
    }

}
