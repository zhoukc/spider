package com.zhaoyouhua.spider.webCollector;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.zhaoyouhua.spider.request.BaiDuRequest;
import com.zhaoyouhua.spider.util.UrlUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;

public class WebCollectorBaiDu extends BreadthCrawler {


//    // 自定义的请求插件
//    // 可以自定义User-Agent和Cookie
//    public  class MyRequester extends OkHttpRequester {
//
//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
//
//        // 每次发送请求前都会执行这个方法来构建请求
//        @Override
//        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
//            // 这里使用的是OkHttp中的Request.Builder
//            // 可以参考OkHttp的文档来修改请求头
//            return super.createRequestBuilder(crawlDatum)
//                    .addHeader("User-Agent", userAgent);
//        }
//
//    }


    public WebCollectorBaiDu(String crawlPath) throws Exception {
        super(crawlPath, true);

    }


    @Override
    public void visit(Page page, CrawlDatums next) {
        // 如果遇到301或者302，手动跳转（将任务加到next中）
        // 并且复制任务的meta
        if (page.code() == 301 || page.code() == 302) {
            next.addAndReturn(page.location()).meta(page.meta());
            return;
        }

        String keyword = page.meta("keyword");
        int pageNum = page.metaAsInt("pageNum");


        if (page.matchType("search")) {
            System.out.println("成功抓取关键词" + keyword + "的第" + pageNum + "页搜索结果");
            Document doc = page.doc();
            Elements result = doc.getElementsByClass("c-container");
            for (int rank = 0; rank < result.size(); rank++) {

                Elements elements = result.get(rank).getElementsByClass("c-showurl");
                Element a = elements.get(0);
                System.out.println("第" + pageNum + "页,第" + (rank + 1) + "个" + a.ownText());
                System.out.println(UrlUtil.getDomainName(a.ownText()));

            }
            System.err.println(result.size());
        }
//        else if (page.matchType("outlink")) {
//
//            int rank = page.metaAsInt("rank");
//            String referer=page.meta("referer");
//
//            String line = String.format("第%s页第%s个结果:%s(%s字节)\tdepth=%s\treferer=%s",
//                    pageNum, rank + 1, page.doc().title(),page.content().length,"", referer);
//            System.out.println(line);
//
//        }
    }


    public static String createBaiDuUrl(String keyword, int pageNum) throws Exception {
        int first = (pageNum - 1) * 10;
        keyword = URLEncoder.encode(keyword, "utf-8");
        return String.format("http://www.baidu.com/s?wd=%s&pn=%s", keyword, first);
    }

    public static void main(String[] args) throws Exception {

        WebCollectorBaiDu webCollectorBaiDu = new WebCollectorBaiDu("My");
        webCollectorBaiDu.setRequester(new BaiDuRequest(false));
        CrawlDatums crawlDatums = new CrawlDatums();

        for (int pageNum = 1; pageNum <= 2; pageNum++) {
            String url = createBaiDuUrl("轴流风机", pageNum);
            CrawlDatum datum = new CrawlDatum(url)
                    .type("search")
                    .meta("keyword", "轴流风机")
                    .meta("pageNum", pageNum);
            crawlDatums.add(datum);
        }
        webCollectorBaiDu.addSeed(crawlDatums);
        webCollectorBaiDu.start(1);

        int threads = webCollectorBaiDu.getThreads();
        System.out.println(threads + ">>>>>>>>>>");

    }
}
