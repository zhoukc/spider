package com.zhaoyouhua.spider.schedule;

import com.zhaoyouhua.spider.jobs.OffLineCrawlJob;
import com.zhaoyouhua.spider.jobs.OnLineCrawlJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzSchedule {


    @Bean
    public JobDetail onLineCrawlerJobDetail() {
        return JobBuilder.newJob(OnLineCrawlJob.class).withIdentity("onLineCrawlerJobDetail", "onLineCrawler").storeDurably().build();
    }

    @Bean
    public Trigger onLineCrawlerTrigger() {
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0 0 10 * * ?"); //每天早上10点执行一次
        return TriggerBuilder.newTrigger().forJob(onLineCrawlerJobDetail()).withIdentity("onLineCrawlerTrigger", "onLineCrawler").withSchedule(cronSchedule).build();
    }


    @Bean
    public JobDetail offLineCrawlerJobDetail() {
        return JobBuilder.newJob(OffLineCrawlJob.class).withIdentity("offLineCrawlerJobDetail", "offLineCrawler").storeDurably().build();
    }

    @Bean
    public Trigger offLineCrawlerTrigger() {
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0 0 10 * * ?"); //每天早上10点执行一次
        return TriggerBuilder.newTrigger().forJob(offLineCrawlerJobDetail()).withIdentity("offLineCrawlerTrigger", "offLineCrawler").withSchedule(cronSchedule).build();
    }


    @Bean
    public JobDetail onLineCrawlerJobDetail2() {
        return JobBuilder.newJob(OnLineCrawlJob.class).withIdentity("onLineCrawlerJobDetail2", "onLineCrawler").storeDurably().build();
    }

    @Bean
    public Trigger onLineCrawlerTrigger2() {
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0 0 18 * * ?"); //每天下午18点执行一次
        return TriggerBuilder.newTrigger().forJob(onLineCrawlerJobDetail2()).withIdentity("onLineCrawlerTrigger2", "onLineCrawler").withSchedule(cronSchedule).build();
    }


    @Bean
    public JobDetail offLineCrawlerJobDetail2() {
        return JobBuilder.newJob(OffLineCrawlJob.class).withIdentity("offLineCrawlerJobDetail2", "offLineCrawler").storeDurably().build();
    }

    @Bean
    public Trigger offLineCrawlerTrigger2() {
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0 0 18 * * ?"); //每天下午18点执行一次
        return TriggerBuilder.newTrigger().forJob(offLineCrawlerJobDetail2()).withIdentity("offLineCrawlerTrigger2", "offLineCrawler").withSchedule(cronSchedule).build();
    }

}
