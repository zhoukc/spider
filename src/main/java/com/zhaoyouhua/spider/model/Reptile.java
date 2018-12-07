package com.zhaoyouhua.spider.model;

public class Reptile {

    private long userDemandId;
    private long userId;
    private int engineType;
    private String keywords;
    private String website;
    private int wordFeeType;
    private int demandFeeType;
    private long keywordsId;

    public Reptile() {

    }

    public Reptile(String keywords, String website, int engineType) {
        this.keywords = keywords;
        this.website = website;
        this.engineType = engineType;
    }

    public long getUserDemandId() {
        return userDemandId;
    }

    public void setUserDemandId(long userDemandId) {
        this.userDemandId = userDemandId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getEngineType() {
        return engineType;
    }

    public void setEngineType(int engineType) {
        this.engineType = engineType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getWordFeeType() {
        return wordFeeType;
    }

    public void setWordFeeType(int wordFeeType) {
        this.wordFeeType = wordFeeType;
    }

    public int getDemandFeeType() {
        return demandFeeType;
    }

    public void setDemandFeeType(int demandFeeType) {
        this.demandFeeType = demandFeeType;
    }

    public long getKeywordsId() {
        return keywordsId;
    }

    public void setKeywordsId(long keywordsId) {
        this.keywordsId = keywordsId;
    }

    // 重写toString方法
    @Override
    public String toString() {
        return "Reptile [keywords=" + keywords + ", website=" + website + ",engineType=" + engineType + ",keywordsId=" + keywordsId + "]";
    }

}
