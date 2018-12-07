package com.zhaoyouhua.spider;

import com.zhaoyouhua.spider.exception.ResultException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 全局异常处理
 * create by zhoukc
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResultException.class)
    @ResponseStatus(HttpStatus.OK)
    public Map businessException(ResultException e) {
        Map map = new HashMap();
        map.put("resultStates", 0);
        map.put("pageNum", e.getPageNum());
        map.put("position", e.getMessage());
        return map;
    }

}
