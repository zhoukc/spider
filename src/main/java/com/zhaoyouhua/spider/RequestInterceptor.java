package com.zhaoyouhua.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Enumeration;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    /**
     * 请求处理前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!logger.isInfoEnabled()) {
            return true;
        }
        request.setAttribute("__requestStartTime", System.currentTimeMillis());
        return true;

    }

    /**
     * 请求处理完执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!logger.isInfoEnabled()) {
            return;
        }
        long finished = System.currentTimeMillis();
        long start = (Long) request.getAttribute("__requestStartTime");
        String ip = getIp(request);
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String heads = getHeads(request);
        logger.info("ip:{}||||url:{}||||method:{}||||head:{}||||runTime:{}", ip, url, method, heads, finished - start);
    }

    private String getHeads(HttpServletRequest request) {
        StringBuilder result = new StringBuilder();
        Enumeration<String> headNames = request.getHeaderNames();
        while (headNames.hasMoreElements()) {
            String name = headNames.nextElement();
            result.append(MessageFormat.format("{0}={1};", name, request.getHeader(name)));
        }
        return result.toString();
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
