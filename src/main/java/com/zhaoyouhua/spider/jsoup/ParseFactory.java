package com.zhaoyouhua.spider.jsoup;

import com.zhaoyouhua.spider.constant.ClientType;
import com.zhaoyouhua.spider.constant.SearchEngine;
import com.zhaoyouhua.spider.constant.SearchEngineType;
import com.zhaoyouhua.spider.model.QueryItem;
import com.zhaoyouhua.spider.model.ResultRank;
import com.zhaoyouhua.spider.model.ro.SpiderInput;
import com.zhaoyouhua.spider.parse.DocParser2;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class ParseFactory {


    public ResultRank crawlerForCRM(SpiderInput input) {
        SearchEngine searchEngine = SearchEngineType.getSearchEngine(input.getEngineType());
        QueryItem queryItem = new QueryItem();
        queryItem.setSearchEngine(searchEngine);
        queryItem.setWebsite(input.getWebsite());
        queryItem.setKeyword(input.getKeyword());
        ResultRank resultRank = ResultRank.instance(queryItem);
        if (input.getEngineType() == 2) {
            queryItem.setClientType(ClientType.MOBILE);
        }
        handle(input.isFirst(), input.getEngineType(), resultRank);
        return resultRank;
    }

    public void handle(boolean isFirst, int engineType, ResultRank resultRank) {
        if (isFirst) {
            switch (engineType) {
                case 1:
                    Document document = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), 1, false, false, null);
                    DocParser2.parseBaiDu(document, 1, resultRank);
                    break;
                case 2:
                    Document document2 = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), 1, true, false, null);
                    DocParser2.parseBaiDu(document2, 1, resultRank);
                    break;
                case 4:
                    Document document3 = JsoupFactory.getSogouCrawler(resultRank.getQueryItem().getKeyword(), 1, false, null);
                    DocParser2.parseSouGou(document3, 1, resultRank);
                    break;
                case 8:
                    Document document4 = JsoupFactory.getSo360Crawler(resultRank.getQueryItem().getKeyword(), 1, false, null);
                    DocParser2.parseSO360(document4, 1, resultRank);
                    break;
            }


        } else {
            int pageNum = 1;
            do {
                switch (engineType) {
                    case 1:
                        Document document = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), pageNum, false, false, null);
                        DocParser2.parseBaiDu(document, pageNum, resultRank);
                        break;
                    case 2:
                        Document document2 = JsoupFactory.getBaiDuCrawler(resultRank.getQueryItem().getKeyword(), pageNum, true, false, null);
                        DocParser2.parseBaiDu(document2, pageNum, resultRank);
                        break;
                    case 4:
                        Document document3 = JsoupFactory.getSogouCrawler(resultRank.getQueryItem().getKeyword(), pageNum, true, null);
                        DocParser2.parseSouGou(document3, pageNum, resultRank);
                        break;
                    case 8:
                        Document document4 = JsoupFactory.getSo360Crawler(resultRank.getQueryItem().getKeyword(), pageNum, true, null);
                        DocParser2.parseSO360(document4, pageNum, resultRank);
                        break;
                }

                pageNum++;

            } while ((resultRank.getPageNum() == -1 && pageNum <= 2));

        }

    }


//    public static void main(String[] args) {
//        File directory = new File("crawlerLog"+File.separator+getCurrentDate());
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//        String absolutePath = directory.getAbsolutePath();
//        File file = new File(absolutePath +File.separator+ "dd.txt");
//        System.out.println(file.getPath());
//    }
//
//    private static String getCurrentDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        return formatter.format(LocalDateTime.now());
//    }
}