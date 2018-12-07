package com.zhaoyouhua.spider.constant;

import java.util.stream.Stream;

/**
 * @Description 搜索引擎枚举
 * create by zhoukc
 */
public enum SearchEngineType {
    BAIDU_PC(1, "百度PC"),
    BAIDU_MOBILE(2, "百度移动"),
    SOUGOU_PC(4, "搜狗PC"),
    E360_PC(8, "360搜索"),
    GOOGLE_PC(16, "谷歌PC");

    private int index;
    private String message;

    SearchEngineType(int index, String message) {
        this.index = index;
        this.message = message;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static SearchEngine getSearchEngine(int engineType) {
        if (1 == engineType) {
            return SearchEngine.BAIDU;
        } else if (2 == engineType) {
            return SearchEngine.BAIDU;
        } else if (4 == engineType) {
            return SearchEngine.SOGOU;
        } else if (8 == engineType) {
            return SearchEngine.SO360;
        }
        return SearchEngine.BAIDU; //默认百度
    }
}