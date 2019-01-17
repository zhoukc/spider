package com.zhaoyouhua.spider.selenium;

import com.alibaba.fastjson.JSON;
import com.zhaoyouhua.spider.model.ResultRank;
import com.zhaoyouhua.spider.util.UrlUtil;
import com.zhaoyouhua.spider.webdriver.MyWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SeleniumFactory {


    public static void getBaiduDoc(String keyword, ResultRank resultRank, boolean isFirst) {
        WebDriver fireFoxDriver = MyWebDriver.createFireFoxDriver();

        fireFoxDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        fireFoxDriver.get("https://www.baidu.com");
        fireFoxDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        fireFoxDriver.findElement(By.id("kw")).clear();
        fireFoxDriver.findElement(By.id("kw")).sendKeys(keyword);
        fireFoxDriver.findElement(By.id("su")).click(); //点击按扭
        fireFoxDriver.manage().timeouts().setScriptTimeout(4, TimeUnit.SECONDS);

        String pageSource = fireFoxDriver.getPageSource();
        Document doc = Jsoup.parse(pageSource);
        int pageNum = 1;
        try {
            parseBaidu(resultRank, doc, pageNum);
            if (!isFirst && resultRank.getPosition() == -1) {  //前两页，首页没找找到继续找第二页
                pageNum++;
                fireFoxDriver.findElement(By.id("page")).findElement(By.className("n")).click(); //下一页
                Thread.sleep(2000);
                parseBaidu(resultRank,Jsoup.parse(fireFoxDriver.getPageSource()),pageNum);
            }
        } catch (Exception e) {
            log.error("没有链接", e);
        }
    }










    private static void parseBaidu(ResultRank resultRank, Document doc, int pageNum) {
        Elements containers = doc.getElementsByClass("c-container");//获取每个条目
        for (int position = 0; position < containers.size(); position++) {
            try {
                Element element = containers.get(position);
                Elements a = element.getElementsByClass("c-showurl");
                String site = a.get(0).ownText();
                if (judgeRank(pageNum, resultRank, (position + 1), element, site)) {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                log.error("百度PC-没有条目");
            }
        }
    }

    private static boolean judgeRank(int pageNum, ResultRank resultRank, int i, Element element, String site) {
        String domainName = UrlUtil.getDomainName(site);

        if (UrlUtil.getDomainName(resultRank.getQueryItem().getWebsite()).equalsIgnoreCase(domainName)) {
            log.info(resultRank.getQueryItem().getKeyword() + ";第" + pageNum + "页,排名第" + i + ";URL:" + domainName);
            resultRank.setPageNum(pageNum);
            resultRank.setPosition(i);
            saveSnapshot(element, pageNum, site, resultRank);
            return true;
        }
        return false;
    }

    private static void saveSnapshot(Element result, final int pageNum, final String site, ResultRank resultRank) {
        StringBuffer sb = new StringBuffer();
        sb.append("----快照摘要----" + "\n");
        sb.append("搜索引擎=" + resultRank.getQueryItem().getSearchEngine() + "\n");
        sb.append("Domain[原始]=" + resultRank.getQueryItem().getWebsite() + "\n");
        sb.append("site[页面解析]=" + site + "\n");
        sb.append("word=" + resultRank.getQueryItem().getKeyword() + "\n");
        sb.append("page=" + pageNum + "\n");
        sb.append("ResultRank=" + JSON.toJSONString(resultRank) + "\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("----快照信息----" + "\n");
        try {
            sb.append(result.html() + "\n");
            File directory = new File("crawlerLog" + File.separator + getCurrentDate());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String absolutePath = directory.getAbsolutePath();
            File file = new File(absolutePath + File.separator + "snap_" + resultRank.getQueryItem().getSearchEngine() + "_" + resultRank.getQueryItem().getKeyword().trim() + "_" + getCurrentTime() + ".log.txt");
            FileUtils.writeStringToFile(file, sb.toString());
            log.info("[OK]本次排名查询成功搜索引擎=" + resultRank.getQueryItem().getSearchEngine() + ",页面快照已经保存在" + file.getAbsolutePath() + "文件中");
        } catch (Exception e) {
            log.error("[FAIL]本次排名查询成功,但是页面快照保存失败", e);

        }

    }

    private static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(LocalDateTime.now());
    }

    private static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return formatter.format(LocalDateTime.now());
    }
}
