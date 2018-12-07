package com.zhaoyouhua.spider.constant;

/**
 * @Description 需求扣费枚举
 * create by zhoukc
 */
public enum DemandFeeType {

    TWO_PAGE_DAY(1, "前两页按天扣费"),
    HOME_PAGE_DAY(2, "首页按天扣费"),
    BAG_DAY(3, "词包按天扣费"),
    BAG_YEAR(4, "包年扣费");

    private int index;
    private String message;

    DemandFeeType(int index, String message) {
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

    public static boolean isHomePage(int feeType) {
        return feeType == HOME_PAGE_DAY.index;
    }
}