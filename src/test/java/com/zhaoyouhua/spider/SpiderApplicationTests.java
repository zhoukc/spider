package com.zhaoyouhua.spider;

import com.zhaoyouhua.spider.jobs.OffLineCrawlJob;
import com.zhaoyouhua.spider.jobs.OnLineCrawlJob;
import com.zhaoyouhua.spider.jsoup.JsoupFactory;
import com.zhaoyouhua.spider.parse.DocParser2;
import org.jsoup.nodes.Document;
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
//
//    @Test
//    public void contextLoads() {
//        onLineCrawlJob.getCrawlData();
//
//    }


    @Test
    public void Test() throws Exception {

//        Document document = JsoupFactory.getBaiDuCrawler("氟胶O型圈厂家", 1, false, false, null);
//        DocParser2.parseBaiDu(document,);
    }

}
