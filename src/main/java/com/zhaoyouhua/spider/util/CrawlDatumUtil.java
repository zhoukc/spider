package com.zhaoyouhua.spider.util;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import com.zhaoyouhua.spider.constant.DemandFeeType;
import com.zhaoyouhua.spider.constant.SearchEngine;
import com.zhaoyouhua.spider.model.QueryItem;
import com.zhaoyouhua.spider.model.Reptile;
import com.zhaoyouhua.spider.request.BaiDuRequest;
import com.zhaoyouhua.spider.request.So360Request;
import com.zhaoyouhua.spider.request.SouGouRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CrawlDatumUtil {

    public static CrawlDatums getSeed(QueryItem queryItem, OkHttpRequester requester, boolean isFirst, boolean isMobile) {
        if (isFirst) {
            CrawlDatums crawlDatums = getCrawlDatum(queryItem, requester, isMobile, 1);
            if (crawlDatums != null) return crawlDatums;
        }
        if (!isFirst) { //如果不是首页，那就会搜索前两页
            CrawlDatums crawlDatums = getCrawlDatum(queryItem, requester, isMobile, 2);
            if (crawlDatums != null) return crawlDatums;
        }

        return null;
    }

    private static CrawlDatums getCrawlDatum(QueryItem queryItem, OkHttpRequester requester, boolean isMobile, int pageNum) {
        if (requester instanceof BaiDuRequest) {
            try {

                CrawlDatums crawlDatums = new CrawlDatums();
                for (int i = 1; i <= pageNum; i++) {
                    String url;
                    if (isMobile) {
                        url = ((BaiDuRequest) requester).createBaiDuMobileUrl(queryItem.getKeyword(), i);
                    } else {
                        url = ((BaiDuRequest) requester).createBaiDuPcUrl(queryItem.getKeyword(), i);
                    }
                    CrawlDatum crawlDatum = new CrawlDatum(url);
                    crawlDatum.type(SearchEngine.BAIDU.toString())
                            .meta("keyword", queryItem.getKeyword()).meta("pageNum", i);
                    crawlDatums.add(crawlDatum);
                }

                return crawlDatums;
            } catch (Exception e) {
                log.error("编码异常", e);
            }
        }
        if (requester instanceof SouGouRequest) {
            try {
                CrawlDatums crawlDatums = new CrawlDatums();
                for (int i = 1; i <= pageNum; i++) {
                    String url = ((SouGouRequest) requester).createSoGouUrl(queryItem.getKeyword(), i);
                    CrawlDatum crawlDatum = new CrawlDatum(url);
                    crawlDatum.type(SearchEngine.SOGOU.toString())
                            .meta("keyword", queryItem.getKeyword()).meta("pageNum", i);
                    crawlDatums.add(crawlDatum);
                }
                return crawlDatums;
            } catch (Exception e) {
                log.error("编码异常", e);
            }
        }

        if (requester instanceof So360Request) {
            try {
                CrawlDatums crawlDatums = new CrawlDatums();
                for (int i = 1; i <= pageNum; i++) {
                    String url = ((So360Request) requester).createSo360Url(queryItem.getKeyword(), i);
                    CrawlDatum crawlDatum = new CrawlDatum(url);
                    crawlDatum.type(SearchEngine.SO360.toString())
                            .meta("keyword", queryItem.getKeyword()).meta("pageNum", i);
                    crawlDatums.add(crawlDatum);
                }
                return crawlDatums;
            } catch (Exception e) {
                log.error("编码异常", e);
            }
        }
        return null;
    }

    public static List<Reptile> getReptiles(List<Reptile> list) {
        List<Reptile> newList = new ArrayList<>();
        //包年、词包关键词分割
        for (Reptile reptile : list) {

            if (reptile.getDemandFeeType() == DemandFeeType.TWO_PAGE_DAY.getIndex() ||
                    reptile.getDemandFeeType() == DemandFeeType.HOME_PAGE_DAY.getIndex()) {
                newList.add(reptile);
                continue;
            }
            String[] keys = StringUtils.split(reptile.getKeywords(), ",");
            for (String key : keys) {
                Reptile ds = new Reptile();
                ds.setDemandFeeType(reptile.getDemandFeeType());
                ds.setEngineType(reptile.getEngineType());
                ds.setKeywords(key);
                ds.setKeywordsId(reptile.getKeywordsId());
                ds.setUserDemandId(reptile.getUserDemandId());
                ds.setUserId(reptile.getUserId());
                ds.setWebsite(reptile.getWebsite());
                ds.setWordFeeType(reptile.getWordFeeType());
                newList.add(ds);
            }
        }
        return newList;
    }

}
