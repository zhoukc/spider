package com.zhaoyouhua.spider.api;

import com.alibaba.fastjson.JSONObject;
import com.zhaoyouhua.spider.jsoup.ParseFactory;
import com.zhaoyouhua.spider.model.ResultRank;
import com.zhaoyouhua.spider.model.ro.SpiderInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {

    @Autowired
    private ParseFactory parseFactory;

    @PostMapping("spider")
    public JSONObject spider(@RequestBody SpiderInput input) {
        JSONObject object = new JSONObject();
        ResultRank resultRank = parseFactory.crawlerForCRM(input);
        object.put("resultStates", 0);
        object.put("pageNum", resultRank.getPageNum());
        object.put("position", resultRank.getPosition());
        return object;
    }




}
