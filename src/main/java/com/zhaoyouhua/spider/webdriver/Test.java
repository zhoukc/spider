package com.zhaoyouhua.spider.webdriver;

import com.zhaoyouhua.spider.util.UrlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) throws Exception{

        WebDriver ieDriver = MyWebDriver.createIEDriver();

        ieDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        ieDriver.get("https://www.baidu.com/");

        ieDriver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        ieDriver.findElement(By.id("kw")).sendKeys("西安蟑螂药");
        ieDriver.findElement(By.id("su")).click(); //点击按扭

        ieDriver.manage().timeouts().setScriptTimeout(4, TimeUnit.SECONDS);

//        List<WebElement> content_left = ieDriver.findElement(By.id("content_left")).findElements(By.className("c-container"));
//
//        for (WebElement webElement : content_left) {
//
//            String site = webElement.findElement(By.className("c-showurl")).getText();
//            String domainName = UrlUtil.getDomainName(site);
//            System.out.println(domainName);
//        }


        //  Thread.sleep(1000);
        String pageSource = ieDriver.getPageSource();

        //Thread.sleep(5000);


       // 西安蟑螂药
        Document doc = Jsoup.parse(pageSource); //JsoupFactory.getBaiDuCrawler("家庭有蟑螂怎么办", 1, false, false, null);

        try {
            Element results = doc.getElementById("content_left");
            Elements resultContent = results.getElementsByClass("c-container");//获取每个条目

            for (int i = 0; i < resultContent.size(); i++) {
                try {
                    Element element = resultContent.get(i);
                    //  Elements f13 = element.getElementsByClass("f13");
                    String site = element.getElementsByClass("c-showurl").get(0).ownText();
                    String domainName = UrlUtil.getDomainName(site);
                    System.out.println(domainName);
//                if (UrlUtil.getDomainName(resultRank.getQueryItem().getWebsite()).equalsIgnoreCase(domainName)) {
////                    log.info(resultRank.getQueryItem().getKeyword() + ";第" + pageNum + "页,排名第" + i + ";URL:" + domainName);
////                    resultRank.setPageNum(pageNum);
////                    resultRank.setPosition((i + 1));
////                    saveSnapshot(element, (i + 1), site, resultRank);
////                    break;
////                }
                } catch (NullPointerException e) {
                    System.out.println(e);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e);
                }

            }

        } catch (Exception e) {
            System.out.println("没有链接");

        }

        ieDriver.close();
        //ieDriver.quit();

    }


}
