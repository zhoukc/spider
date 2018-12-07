package com.zhaoyouhua.spider.dao.model;

import com.zhaoyouhua.spider.dao.mybatis.Table;
import lombok.Getter;
import lombok.Setter;

@Table("reptilianrecord")
@Getter
@Setter
public class ReptilianRecord extends BaseModel {

    private long id;
    private long userDemandId;
    private long userId;
    private String website;
    private int engineType;
    private String keyword;
    private int pagination;
    private int pagemark;
    private int feeType;
    private int wordFeeType;
    private long keywordsId;

}