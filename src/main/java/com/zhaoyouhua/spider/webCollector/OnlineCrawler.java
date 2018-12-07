package com.zhaoyouhua.spider.webCollector;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.alibaba.fastjson.JSON;
import com.zhaoyouhua.spider.constant.SearchEngine;
import com.zhaoyouhua.spider.model.QueryItem;
import com.zhaoyouhua.spider.model.ResultRank;
import com.zhaoyouhua.spider.parse.DocParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OnlineCrawler extends BreadthCrawler {

    private QueryItem queryItem;

    public QueryItem getQueryItem() {
        return queryItem;
    }

    public void setQueryItem(QueryItem queryItem) {
        this.queryItem = queryItem;
    }

    public OnlineCrawler(String crawlPath) {
        super(crawlPath, true);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        // 如果遇到301或者302，手动跳转（将任务加到next中）
        // 并且复制任务的meta
        if (page.code() == 301 || page.code() == 302) {
            next.addAndReturn(page.location()).meta(page.meta());
            return;
        }

        synchronized (this) {

            if (page.matchType(SearchEngine.BAIDU.toString())) {
                ResultRank instance = ResultRank.instance(queryItem);
                DocParser.parseBaiDu(page, instance);
                if (instance.getPageNum() != -1) {
                    log.info(JSON.toJSONString(instance));
                    //TODO 入库
                }
            }

            if (page.matchType(SearchEngine.SO360.toString())) {
                ResultRank instance = ResultRank.instance(queryItem);
                DocParser.parseSO360(page, instance);
                if (instance.getPageNum() != -1) {
                    log.info(JSON.toJSONString(instance));
                    //TODO 入库
                }
            }

            if (page.matchType(SearchEngine.SOGOU.toString())) {
                ResultRank instance = ResultRank.instance(queryItem);
                DocParser.parseSouGou(page, instance);
                if (instance.getPageNum() != -1) {
                    log.info(JSON.toJSONString(instance));
                    //TODO 入库
                }
            }

        }

    }


}
