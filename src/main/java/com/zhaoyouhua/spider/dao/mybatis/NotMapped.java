package com.zhaoyouhua.spider.dao.mybatis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *@Description 入库时会忽略加了该注解的字段
 *create by zhoukc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
public @interface NotMapped {
}
