package com.zhaoyouhua.spider.dao;

import com.zhaoyouhua.spider.dao.model.ReptilianRecord;
import com.zhaoyouhua.spider.dao.mybatis.IBaseDao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IReptilianRecordDao extends IBaseDao<ReptilianRecord> {


    @Delete(" delete from reptilianrecord where id in (" +
            "select a.id from (" +
            "select min(id) id from reptilianrecord where date_format(createdTime,'%y-%m-%d') = date_format(now(),'%y-%m-%d') " +
            "group by  keywordsId,website,keyword,engineType HAVING count(*) >1)a)")
    int deleteToadyRepeatRecord();

    @Select("select count(*) from reptilianrecord where createdTime>=#{today}")
    int countReptilianRecords(Date today);
}
