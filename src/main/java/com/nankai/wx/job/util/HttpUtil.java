package com.nankai.wx.job.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

/**
 * Created by michealyang on 17/5/4.
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 获得某个参数的值
     * @param request
     * @param name
     * @return
     */
    public static String getParameterValue(HttpServletRequest request, String name) {
        Preconditions.checkArgument(request != null);
        if(StringUtils.isBlank(name)) return null;
        Map<String, String> parameterMap = getParameterMap(request);
        if(CollectionUtils.isEmpty(parameterMap)) return null;
        return parameterMap.get(name);
    }

    /**
     * 获得uri中的所有参数及其对应的值
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request){
        Preconditions.checkArgument(request != null);
        String url = request.getQueryString();
        if(StringUtils.isBlank(url)) return Collections.emptyMap();
        return Splitter.on("&").trimResults().withKeyValueSeparator("=").split(url);
    }

    public static String getHost(String url, String uri){
        if(StringUtils.isBlank(url)) return url;
        if(StringUtils.isBlank(uri) || "/".equals(uri)) return url;
        int idx = url.indexOf(uri);
        if(idx == -1) return url;
        return url.substring(0, idx);
    }

    public static String formatUrl(String host, String uri){
        Preconditions.checkArgument(StringUtils.isNotBlank(host));
        try {
            if(StringUtils.isBlank(uri)){
                return URLEncoder.encode(host, "utf-8");
            }
            if(host.charAt(host.length()-1) == '/' && uri.charAt(0) == '/') {
                return host.substring(0, host.length() - 1) + uri;
            }
            if(host.charAt(host.length()-1) != '/' && uri.charAt(0) != '/') {
                return host + "/" + uri;
            }
            return URLEncoder.encode(host + uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("[formatUrl] e=#{}", e);
            return "";
        }
    }

    /**
     * get方式调用接口 default 连接请求超时=3s , 连接超时=3s , 读超时=3s
     * @param url
     * @return
     */
    public static String doGet(String url) {
        HttpGet get = new HttpGet(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(3000).setConnectTimeout(3000)
                .setSocketTimeout(3000);
        get.setConfig(builder.build());

        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(get)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.error("[doGet] e=#{}", e);
            return null;

        }
    }

//    public static String doGetWithOkHttp(String url){
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try {
//            Response response = null;
//            response = client.newCall(request).execute();
//            return response.body().string();
//        } catch (IOException e) {
//            logger.error("[doGetWithOkHttp] e=#{}", e);
//        }
//        return null;
//    }

    /**
     * get请求方式，可自定义超时时间
     * @param url
     * @param connTimeout
     * @param connReqTimeout
     * @param socketTimeout
     * @return
     */
    public static String doGet(String url, int connTimeout, int connReqTimeout, int socketTimeout) {
        HttpGet get = new HttpGet(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connReqTimeout).setConnectTimeout(connTimeout)
                .setSocketTimeout(socketTimeout);
        get.setConfig(builder.build());

        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(get)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.error("[doGet] e=#{}", e);
            return null;

        }
    }


    /**
     * post方法，default 连接请求超时=3s , 连接超时=3s , 读超时=3s
     * @param url
     * @param entity
     * @return
     */
    public static String doPost(String url, HttpEntity entity) {
        HttpPost post = new HttpPost(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(3000).setConnectTimeout(3000)
                .setSocketTimeout(3000);
        post.setConfig(builder.build());

        post.setEntity(entity);

        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.error("[doPost] e=#{}", e);
            return null;
        }
    }

    /**
     * post方法，可自定义超时时间
     * @param url
     * @param entity
     * @param connTimeout
     * @param connReqTimeout
     * @param socketTimeout
     * @return
     */
    public static String doPost(String url,
                                HttpEntity entity,
                                int connTimeout,
                                int connReqTimeout,
                                int socketTimeout){
        HttpPost post = new HttpPost(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connReqTimeout).setConnectTimeout(connTimeout)
                .setSocketTimeout(socketTimeout);
        post.setConfig(builder.build());

        post.setEntity(entity);

        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.error("[doPost] e=#{}", e);
            return null;
        }
    }
}
