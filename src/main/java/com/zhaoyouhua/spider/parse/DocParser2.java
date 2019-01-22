package com.zhaoyouhua.spider.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhaoyouhua.spider.model.ResultRank;
import com.zhaoyouhua.spider.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DocParser2 {


    public static void parseBaiDu(Document doc, int pageNum, ResultRank resultRank) {

        try {
            if (!resultRank.getQueryItem().isMobile()) {
                Elements containers = doc.getElementsByClass("c-container");//获取每个条目
                for (int position = 0; position < containers.size(); position++) {
                    try {
                        Element element = containers.get(position);
                        Elements a = element.getElementsByClass("c-showurl");
                        String site = a.get(0).ownText();
                        if (judgeRank(pageNum, resultRank, (position + 1), element, site)) break;
                    } catch (Exception e) {
                        log.error("百度PC-没有条目");
                    }
                }

            } else {
                parseBaiDuMobile(doc, pageNum, resultRank);
            }
        } catch (Exception e) {
            log.error("没有链接");
        }

    }

    private static void parseBaiDuMobile(Document doc, int pageNum, ResultRank resultRank) {
        try {
            Element results = doc.getElementById("results");
            Elements resultContent = results.getElementsByClass("c-result");//获取每个条目

            for (int i = 0; i < resultContent.size(); i++) {

                try {
                    Element element = resultContent.get(i);
                    String data_log = element.attr("data-log");
                    log.info("data_log:" + data_log);
                    JSONObject jsStr = JSONObject.parseObject(data_log);
                    String site = jsStr.getString("mu");
                    String domainName = UrlUtil.getDomainName(site);
                    if (UrlUtil.getDomainName(resultRank.getQueryItem().getWebsite()).equalsIgnoreCase(domainName)) {
                        log.info(resultRank.getQueryItem().getKeyword() + ";第" + pageNum + "页,排名第" + jsStr.getString("order") + ";URL:" + domainName);
                        resultRank.setPageNum(pageNum);
                        resultRank.setPosition(Integer.parseInt(jsStr.getString("order")));
                        saveSnapshot(element, pageNum, site, resultRank);
                        break;
                    }
                } catch (Exception e) {
                    log.error("百度移动-没有条目");
                }

            }

        } catch (Exception e) {
            log.error("没有链接");
        }
    }


    public static void parseSouGou(Document doc, int pageNum, ResultRank resultRank) {

        try {
            Elements results = doc.getElementsByClass("results"); //内容主体
            Elements rbs = results.get(0).getElementsByClass("rb");//每个条目
            for (int i = 0; i < rbs.size(); i++) {
                try {
                    Element element = rbs.get(i);
                    Elements fb = element.getElementsByClass("fb");
                    String site = fb.get(0).child(0).ownText();
                    if (judgeRank(pageNum, resultRank, (i + 1), element, site)) break;
                } catch (Exception e) {
                    log.error("搜狗PC-没有条目");
                }
            }
        } catch (Exception e) {
            log.error("没有链接");
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


    public static void parseSO360(Document doc, int pageNum, ResultRank resultRank) {

        try {
            Elements result = doc.getElementsByClass("result");
            Elements resList = result.get(0).getElementsByClass("res-list");
            for (int pos = 0; pos < resList.size(); pos++) {
                try {
                    Element element = resList.get(pos);
                    Elements li = element.getElementsByClass("res-linkinfo");
                    String site = li.get(0).child(0).ownText();
                    if (judgeRank(pageNum, resultRank, (pos + 1), element, site)) break;
                } catch (Exception e) {
                    log.error("360PC-没有条目");
                }
            }
        } catch (Exception e) {
            log.error("没有链接");
        }

    }


    public static void saveSnapshot(Element result, final int pageNum, final String site, ResultRank resultRank) {
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
