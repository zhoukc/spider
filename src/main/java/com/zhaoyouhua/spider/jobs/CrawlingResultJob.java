package com.zhaoyouhua.spider.jobs;

import com.zhaoyouhua.spider.dao.IOffLineReptilianRecordDao;
import com.zhaoyouhua.spider.dao.IReptilianRecordDao;
import com.zhaoyouhua.spider.mail.JavaMailService;
import com.zhaoyouhua.spider.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

@Slf4j
public class CrawlingResultJob extends QuartzJobBean {

    @Autowired
    private JavaMailService javaMailService;
    @Autowired
    private IOffLineReptilianRecordDao offLineReptilianRecordDao;
    @Autowired
    private IReptilianRecordDao reptilianRecordDao;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("执行发送邮件的定时任务");
        handle();
    }

    private void handle() {

        LocalDateTime currentDayStart = DateUtil.getCurrentDayStart();
        int countOffline = offLineReptilianRecordDao.countTodayOffLineReptilianRecords(DateUtil.localDateTimeToDate(currentDayStart));
        int countOnline =reptilianRecordDao.countReptilianRecords(DateUtil.localDateTimeToDate(currentDayStart));


        LocalDateTime localDateTime = currentDayStart.minusDays(1);

        int countOfflineTotal = offLineReptilianRecordDao.countTodayOffLineReptilianRecords(DateUtil.localDateTimeToDate(localDateTime));
        int countOnlineTotal =reptilianRecordDao.countReptilianRecords(DateUtil.localDateTimeToDate(localDateTime));


        String receiver[]={"gaoyb@newmind.vip","zhangw@newmind.vip","17195877391@163.com"};
        StringBuffer buffer=new StringBuffer();
        buffer.append("今日一级市场爬取关键词数量为："+countOnline);
        buffer.append("\r\n 昨日爬取关键词数量为："+(countOnlineTotal-countOnline));
        buffer.append("\r\n今日二级级市场爬取关键词数量为："+countOffline);
        buffer.append("\r\n 昨日爬取关键词数量为："+(countOfflineTotal-countOffline));

        javaMailService.sendGroupMail("zhoukc@newmind.vip","爬虫爬取结果",buffer.toString(),receiver);

    }

}