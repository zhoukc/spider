package com.zhaoyouhua.spider;

import com.zhaoyouhua.spider.jobs.OffLineCrawlJob;
import com.zhaoyouhua.spider.jobs.OnLineCrawlJob;
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



}
