package com.zhaoyouhua.spider.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultRank {

    private int pageNum = -1;
    private int position = -1;
    private QueryItem queryItem;

    public static ResultRank instance(QueryItem queryItem) {
        ResultRank resultRank = new ResultRank();
        resultRank.queryItem = queryItem;
        return resultRank;
    }

}
