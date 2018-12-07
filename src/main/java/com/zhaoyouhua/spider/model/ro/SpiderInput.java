package com.zhaoyouhua.spider.model.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpiderInput {

    private String keyword;
    private String website;
    private int engineType;
    private int pageNum;
    private boolean first;

}
