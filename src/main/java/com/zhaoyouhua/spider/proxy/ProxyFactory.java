package com.zhaoyouhua.spider.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhaoyouhua.spider.util.HttpUtil;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProxyFactory {

    private static List<JSONObject> proxies = new ArrayList<>();
    private static Random random = new Random();

    static {
//        String result = HttpUtil.sendGet("http://piping.mogumiao.com/proxy/api/get_ip_bs?appKey=ea069ae1edba49fa97134fc2b1cdb8ef&count=20&expiryDate=0&format=1&newLine=2", null);
//        swammIp swammIp = JSON.parseObject(result, swammIp.class);
//        for (ProxyFactory.swammIp.Msg msg : swammIp.getMsg()) {
//            JSONObject object = new JSONObject();
//            object.put("ip", msg.getIp());
//            object.put("port", msg.getPort());
//            proxies.add(object);
//        }
        System.out.println("初始化ip池");
    }


    public static void main(String[] args) {

        boolean b = checkProxyIP("115.46.75.84", 8123);
        System.out.println(b);


    }

    public static boolean checkProxyIP(String ip, int port) {
        try {
            URL url = new URL("http://www.baidu.com");
            InetSocketAddress address = new InetSocketAddress(ip, port);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            URLConnection urlConnection = url.openConnection(proxy);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");
            String con = IOUtils.toString(urlConnection.getInputStream());
            if (con != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static JSONObject randomProxy() {
        int seed = random.nextInt(proxies.size());
        return proxies.get(seed);
    }


    public static class swammIp {

        private int code;
        private List<Msg> msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<Msg> getMsg() {
            return msg;
        }

        public void setMsg(List<Msg> msg) {
            this.msg = msg;
        }

        public class Msg {
            private String port;
            private String ip;

            public String getPort() {
                return port;
            }

            public void setPort(String port) {
                this.port = port;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }
        }


    }


}
