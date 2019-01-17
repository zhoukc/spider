package com.zhaoyouhua.spider.dao;

import com.zhaoyouhua.spider.dao.model.OffLineReptilianRecord;
import com.zhaoyouhua.spider.dao.mybatis.IBaseDao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IOffLineReptilianRecordDao extends IBaseDao<OffLineReptilianRecord> {

    @Delete(" delete from offLinereptilianrecord where id in (" +
            "select a.id from (" +
            "select min(id) id from offLinereptilianrecord where date_format(createdTime,'%y-%m-%d') = date_format(now(),'%y-%m-%d') " +
            "group by  keywordsId,website,keyword,engineType HAVING count(*) >1)a)")
    int deleteToadyRepeatRecord();

    @Select("select count(*) from offLinereptilianrecord where createdTime >=#{today}")
    int countTodayOffLineReptilianRecords(Date today);
}
