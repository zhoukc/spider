package com.zhaoyouhua.spider.model;

import com.zhaoyouhua.spider.constant.ClientType;
import com.zhaoyouhua.spider.constant.SearchEngine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryItem {

    //关键词
    private String keyword;
    //网址
    private String website;
    //客户端类型
    private ClientType clientType = ClientType.PC;
    //搜索引擎类型
    private SearchEngine searchEngine;
    //待爬取数据的对象
    private Reptile reptile;


    public boolean isMobile() {
        return clientType == ClientType.MOBILE;
    }

    public QueryItem() {
    }

    public QueryItem(String keyword, SearchEngine searchEngine) {
        this.keyword = keyword;
        this.searchEngine = searchEngine;
    }
}
