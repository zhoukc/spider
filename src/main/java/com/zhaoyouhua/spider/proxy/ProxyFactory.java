package com.zhaoyouhua.spider.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhaoyouhua.spider.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class ProxyFactory {

    private static List<JSONObject> proxies = new ArrayList<>();
    private static int count = 0;
    private static Random random = new Random();

    static {
        String result = HttpUtil.sendGet("http://piping.mogumiao.com/proxy/api/get_ip_al?appKey=7322ed9cd2a942d2a08621eb01105b69&count=5&expiryDate=0&format=1&newLine=2", null);
        ProxyIP ProxyIP = JSON.parseObject(result, ProxyIP.class);
        saveIP(ProxyIP);
        count++;
        System.out.println("初始化ip池");
    }

    public static void getIP() {
        String result = HttpUtil.sendGet("http://piping.mogumiao.com/proxy/api/get_ip_al?appKey=7322ed9cd2a942d2a08621eb01105b69&count=5&expiryDate=0&format=1&newLine=2", null);
        ProxyIP ProxyIP = JSON.parseObject(result, ProxyIP.class);
        saveIP(ProxyIP);
        count++;
        log.info("第" + count + "次获取ip");
    }

    public static void removeUnlessIP(JSONObject jsonObject) {
        proxies.remove(jsonObject);
    }

    private static void saveIP(ProxyIP proxyIP) {
        for (ProxyIP.Msg msg : proxyIP.getMsg()) {
            JSONObject object = new JSONObject();
            object.put("ip", msg.getIp());
            object.put("port", msg.getPort());
            proxies.add(object);
        }
    }


//    public static void main(String[] args) {
//
//        // JSONObject ip = getIP();
//        //  System.out.println(JSON.toJSONString(ip));
//        boolean b = ProxyFactory.checkProxyIP("117.69.200.193", 35656);
//        System.out.println(b);
//
//        new ProxyFactory();
//    }

    public static boolean checkProxyIP(String ip, int port) {
        try {
            URL url = new URL("http://www.baidu.com");
            InetSocketAddress address = new InetSocketAddress(ip, port);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            URLConnection urlConnection = url.openConnection(proxy);
            urlConnection.setConnectTimeout(5000);
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
        JSONObject jsonObject = null;
        while (true) {
            int seed = random.nextInt(proxies.size());
            jsonObject = proxies.get(seed);
            boolean isAvailable = ProxyFactory.checkProxyIP(jsonObject.getString("ip"), jsonObject.getIntValue("port"));
            if (!isAvailable) {
                proxies.remove(jsonObject);
                if (proxies.size() <= 2) {
                    getIP();//添加新的ip
                }
                continue;
            }
            break;
        }
        return jsonObject;
    }


    public static class ProxyIP {

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
