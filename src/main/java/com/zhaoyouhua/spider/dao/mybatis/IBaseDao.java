package com.zhaoyouhua.spider.dao.mybatis;


import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface IBaseDao<T> {

    @InsertProvider(type = BaseSqlFactory.class, method = "insert")
    int insert(T paramT);

    @UpdateProvider(type = BaseSqlFactory.class, method = "update")
    int update(T paramT);

    @DeleteProvider(type = BaseSqlFactory.class, method = "delete")
    int delete(T paramT);

}
