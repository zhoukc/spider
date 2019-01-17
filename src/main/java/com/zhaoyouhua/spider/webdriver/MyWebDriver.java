package com.zhaoyouhua.spider.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class MyWebDriver {


    public static WebDriver createIEDriver() {
        System.setProperty("webdriver.ie.driver","D:\\webDriver\\IEx64\\IEDriverServer.exe");//iedriver服务地址
        return new InternetExplorerDriver();
    }

    public static WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver","");//chromedriver服务地址
        return new ChromeDriver();
    }

    public static WebDriver createFireFoxDriver() {
        System.setProperty("webdriver.gecko.driver","D:\\webDriver\\geckodriver.exe");//Firefox
        return new FirefoxDriver();
    }
}
