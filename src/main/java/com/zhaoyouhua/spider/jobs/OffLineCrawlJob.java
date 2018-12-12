package com.zhaoyouhua.spider.jobs;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhaoyouhua.spider.constant.ClientType;
import com.zhaoyouhua.spider.constant.DemandFeeType;
import com.zhaoyouhua.spider.constant.SearchEngineType;
import com.zhaoyouhua.spider.dao.model.OffLineReptilianRecord;
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

import java.util.List;

@Slf4j
public class OffLineCrawlJob extends QuartzJobBean {

    @Autowired
    private DataRepository repository;
    @Autowired
    private ParseFactory parseFactory;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        getCrawlData();
    }

    public void getCrawlData() {

        String sr = HttpUtil.sendPost("http://47.97.207.240:8080//getCrawlingOffLineOrder", "");
        JSONObject json = JSONObject.parseObject(sr);
        JSONArray ad = JSONArray.parseArray(json.getString("content"));
        List<Reptile> list = JSONObject.parseArray(ad.toJSONString(), Reptile.class);


        List<Reptile> newList = CrawlDatumUtil.getReptiles(list);

        long startTime = System.currentTimeMillis();
        log.info("线下爬虫开始工作");
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
                OffLineReptilianRecord offLineReptilianRecord = new OffLineReptilianRecord();
                offLineReptilianRecord.setEngineType(reptile.getEngineType());
                offLineReptilianRecord.setFeeType(reptile.getDemandFeeType());
                offLineReptilianRecord.setPagemark(resultRank.getPosition());
                offLineReptilianRecord.setPagination(resultRank.getPageNum());
                offLineReptilianRecord.setKeyword(reptile.getKeywords());
                offLineReptilianRecord.setUserDemandId(reptile.getUserDemandId());
                offLineReptilianRecord.setWebsite(reptile.getWebsite());
                offLineReptilianRecord.setUserId(reptile.getUserId());
                repository.insertOffLineReptilianRecord(offLineReptilianRecord);
            }


        }
        repository.clearTodayRepateOffLineReptilianRecord();
        log.info("线下爬虫结束工作,耗时" + ((System.currentTimeMillis() - startTime) / 1000) + "S");


    }


}
