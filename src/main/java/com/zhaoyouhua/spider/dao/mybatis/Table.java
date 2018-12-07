package com.zhaoyouhua.spider.dao.mybatis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *@Description 如果po类名和数据库表名不一致，可以使用该注解标识和数据库表一致
 *create by zhoukc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface Table {
    String value();
}
