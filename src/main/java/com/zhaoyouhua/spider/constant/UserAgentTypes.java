package com.zhaoyouhua.spider.constant;

import java.util.Map;
import java.util.TreeMap;

public class UserAgentTypes {

    static Map<String, String> userAgent = new TreeMap<String, String>();

    static {
        userAgent.put("LINUX_CHROME", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21");
        userAgent.put("Firefox", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");
        userAgent.put("IE7", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)");
        userAgent.put("IE8", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)");
        userAgent.put("IE9", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        userAgent.put("ANDROID", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Mobile Safari/537.36");
        userAgent.put("IPHONE_IOS7", "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/546.10 (KHTML, like Gecko) Version/6.0 Mobile/7E18WD Safari/8536.25");
        userAgent.put("IPHONE_IOS6", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10A5376e");
        userAgent.put("IPHONE_IOS5", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3");
        userAgent.put("IPHONE_IOS4", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5");
        userAgent.put("IPAD_IOS7", "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53");
        userAgent.put("IPAD_IOS6", "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25");
        userAgent.put("IPAD_IOS5", "Mozilla/5.0 (iPad; CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3");
        userAgent.put("IPAD_IOS4", "Mozilla/5.0 (iPad; CPU OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5");
        userAgent.put("WP7", "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; NOKIA; Lumia 800)");
        userAgent.put("MAC_SAFARI5", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) Version/5.1.3 Safari/534.53.10");
    }

    public static String getHttpUser_AgentByType(String type) {
        if (type == null || type.trim().length() < 1) {
            return userAgent.get("IE9");
        }
        type = type.trim().toUpperCase();
        if (!userAgent.containsKey(type)) {
            return userAgent.get("IE9");
        }
        return userAgent.get(type);
    }

    public static boolean isMobile(String type) {
        if (type == null
                || type.toUpperCase().startsWith("IE")
                || type.toUpperCase().contains("CHROME")
        ) {
            return false;
        } else {
            return true;
        }
    }
}
