package com.zhaoyouhua.spider.webCollector;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;
import com.alibaba.fastjson.JSON;
import com.zhaoyouhua.spider.constant.ClientType;
import com.zhaoyouhua.spider.constant.SearchEngine;
import com.zhaoyouhua.spider.model.QueryItem;
import com.zhaoyouhua.spider.model.ResultRank;
import com.zhaoyouhua.spider.model.ro.SpiderInput;
import com.zhaoyouhua.spider.parse.DocParser;
import com.zhaoyouhua.spider.request.BaiDuRequest;
import com.zhaoyouhua.spider.request.So360Request;
import com.zhaoyouhua.spider.request.SouGouRequest;
import com.zhaoyouhua.spider.util.CrawlDatumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// 供crm使用的爬虫
@Component
@Slf4j
public class InsideCrawler extends RamCrawler {

    private volatile SpiderInput input;

    public static ResultRank resultRank;

    public SpiderInput getInput() {
        return input;
    }

    public void setInput(SpiderInput input) {
        this.input = input;
    }

//    public InsideCrawler() {
//        super("defaultCrawler", true);
//    }
//
//    public InsideCrawler(String crawlPath, boolean autoParse) {
//        super(crawlPath, autoParse);
//    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        // 如果遇到301或者302，手动跳转（将任务加到next中）
        // 并且复制任务的meta
        if (page.code() == 301 || page.code() == 302) {
            next.addAndReturn(page.location()).meta(page.meta());
            return;
        }

        if (page.matchType(SearchEngine.BAIDU.toString())) {
            QueryItem queryItem = new QueryItem();
            queryItem.setSearchEngine(SearchEngine.BAIDU);
            queryItem.setKeyword(input.getKeyword());
            queryItem.setWebsite(input.getWebsite());
            if (input.getEngineType() == 2) {
                queryItem.setClientType(ClientType.MOBILE);
            }
            ResultRank instance = ResultRank.instance(queryItem);
            DocParser.parseBaiDu(page, instance);
            if (instance.getPageNum() != -1) {
                log.info(JSON.toJSONString(instance));
                resultRank = instance;
            }
        }

        if (page.matchType(SearchEngine.SO360.toString())) {
            QueryItem queryItem = new QueryItem();
            queryItem.setSearchEngine(SearchEngine.SO360);
            queryItem.setWebsite(input.getWebsite());
            queryItem.setKeyword(input.getKeyword());
            ResultRank instance = ResultRank.instance(queryItem);
            DocParser.parseSO360(page, instance);
            if (instance.getPageNum() != -1) {
                log.info(JSON.toJSONString(instance));
                resultRank = instance;
            }
        }

        if (page.matchType(SearchEngine.SOGOU.toString())) {
            QueryItem queryItem = new QueryItem();
            queryItem.setSearchEngine(SearchEngine.SOGOU);
            queryItem.setWebsite(input.getWebsite());
            queryItem.setKeyword(input.getKeyword());
            ResultRank instance = ResultRank.instance(queryItem);
            DocParser.parseSouGou(page, instance);
            if (instance.getPageNum() != -1) {
                log.info(JSON.toJSONString(instance));
                resultRank = instance;
            }
        }


    }


    public void startCrawler() {

        switch (input.getEngineType()) {
            case 1:
                BaiDuRequest baiDuRequest = new BaiDuRequest(false);
                CrawlDatums seed = CrawlDatumUtil.getSeed(new QueryItem(input.getKeyword(), SearchEngine.BAIDU), baiDuRequest, input.isFirst(), false);
                this.setRequester(baiDuRequest);
                this.addSeed(seed);
                break;
            case 2:
                BaiDuRequest baiDuMRequest = new BaiDuRequest(true);
                QueryItem queryItem = new QueryItem(input.getKeyword(), SearchEngine.BAIDU);
                queryItem.setClientType(ClientType.MOBILE);
                CrawlDatums seed2 = CrawlDatumUtil.getSeed(queryItem, baiDuMRequest, input.isFirst(), true);
                this.setRequester(baiDuMRequest);
                this.addSeed(seed2);
                break;
            case 4:
                SouGouRequest souGouRequest = new SouGouRequest();
                CrawlDatums seed3 = CrawlDatumUtil.getSeed(new QueryItem(input.getKeyword(), SearchEngine.SOGOU), souGouRequest, input.isFirst(), false);
                this.setRequester(souGouRequest);
                this.addSeed(seed3);
                break;
            case 8:
                So360Request so360Request = new So360Request();
                CrawlDatums seed4 = CrawlDatumUtil.getSeed(new QueryItem(input.getKeyword(), SearchEngine.SO360), so360Request, input.isFirst(), false);
                this.setRequester(so360Request);
                this.addSeed(seed4);
                break;
        }


        try {
            this.start(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
