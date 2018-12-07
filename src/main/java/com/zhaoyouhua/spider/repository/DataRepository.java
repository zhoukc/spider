package com.zhaoyouhua.spider.repository;

import com.zhaoyouhua.spider.dao.IOffLineReptilianRecordDao;
import com.zhaoyouhua.spider.dao.IReptilianRecordDao;
import com.zhaoyouhua.spider.dao.model.OffLineReptilianRecord;
import com.zhaoyouhua.spider.dao.model.ReptilianRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRepository {

    @Autowired
    private IOffLineReptilianRecordDao offLineReptilianRecordDao;
    @Autowired
    private IReptilianRecordDao reptilianRecordDao;


    public int insertOffLineReptilianRecord(OffLineReptilianRecord record) {
        return offLineReptilianRecordDao.insert(record);
    }

    public int insertOffLineReptilianRecordBatch(List<OffLineReptilianRecord> records) {
        return records.stream().map(p -> offLineReptilianRecordDao.insert(p)).reduce(0, (x, y) -> x + y);
    }

    public int insertReptilianRecord(ReptilianRecord record) {
        return reptilianRecordDao.insert(record);
    }

    public int insertReptilianRecordBatch(List<ReptilianRecord> records) {
        return records.stream().map(p -> reptilianRecordDao.insert(p)).reduce(0, (x, y) -> x + y);
    }

    public int clearTodayRepateReptilianRecord() {
        return reptilianRecordDao.deleteToadyRepeatRecord();
    }

    public int clearTodayRepateOffLineReptilianRecord() {
        return offLineReptilianRecordDao.deleteToadyRepeatRecord();
    }
}
