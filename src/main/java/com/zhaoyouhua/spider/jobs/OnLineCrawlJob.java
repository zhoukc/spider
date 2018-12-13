package com.zhaoyouhua.spider.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhaoyouhua.spider.constant.ClientType;
import com.zhaoyouhua.spider.constant.DemandFeeType;
import com.zhaoyouhua.spider.constant.SearchEngineType;
import com.zhaoyouhua.spider.dao.model.ReptilianRecord;
import com.zhaoyouhua.spider.jsoup.ParseFactory;
import com.zhaoyouhua.spider.model.QueryItem;
import com.zhaoyouhua.spider.model.Reptile;
import com.zhaoyouhua.spider.model.ResultRank;
import com.zhaoyouhua.spider.repository.DataRepository;
import com.zhaoyouhua.spider.util.CrawlDatumUtil;
import com.zhaoyouhua.spider.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
//@Component
public class OnLineCrawlJob extends QuartzJobBean {

    @Autowired
    private DataRepository repository;
    @Autowired
    private ParseFactory parseFactory;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        getCrawlData();
    }


    public void getCrawlData() {
        String sr = HttpUtil.sendPost("http://47.97.207.240:8080//getCrawlingUserDemands", "");
        JSONObject json = JSONObject.parseObject(sr);
        JSONArray ad = JSONArray.parseArray(json.getString("content"));
        List<Reptile> list = JSONObject.parseArray(ad.toJSONString(), Reptile.class);


        List<Reptile> newList = CrawlDatumUtil.getReptiles(list);

        long startTime = System.currentTimeMillis();
        log.info("线上爬虫开始工作");
        for (Reptile reptile : newList) {

            QueryItem queryItem = new QueryItem();
            queryItem.setSearchEngine(SearchEngineType.getSearchEngine(reptile.getEngineType()));
            queryItem.setKeyword(reptile.getKeywords());
            queryItem.setWebsite(reptile.getWebsite());
            if (reptile.getEngineType() == 2) {
                queryItem.setClientType(ClientType.MOBILE);
            }
            ResultRank resultRank = ResultRank.instance(queryItem);
            parseFactory.handle(DemandFeeType.isHomePage(reptile.getDemandFeeType()), reptile.getEngineType(), resultRank);

            if (resultRank.getPageNum() != -1) {
                ReptilianRecord reptilianRecord = new ReptilianRecord();
                reptilianRecord.setEngineType(reptile.getEngineType());
                reptilianRecord.setFeeType(reptile.getDemandFeeType());
                reptilianRecord.setPagemark(resultRank.getPosition());
                reptilianRecord.setPagination(resultRank.getPageNum());
                reptilianRecord.setKeyword(reptile.getKeywords());
                reptilianRecord.setUserDemandId(reptile.getUserDemandId());
                reptilianRecord.setWebsite(reptile.getWebsite());
                reptilianRecord.setUserId(reptile.getUserId());
                repository.insertReptilianRecord(reptilianRecord);
            }


        }
        repository.clearTodayRepateReptilianRecord();
        log.info("线上爬虫结束工作,耗时" + ((System.currentTimeMillis() - startTime) / 1000) + "S");

    }


}
