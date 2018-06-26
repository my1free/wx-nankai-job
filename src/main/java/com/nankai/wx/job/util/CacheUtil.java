package com.nankai.wx.job.util;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CacheUtil {

    private static ConcurrentHashMap<String, CacheItem> cache = new ConcurrentHashMap<>(5000);

    public static boolean set(String key, JSONObject value, int expire) {
        CacheItem item = new CacheItem(value, expire);
        if (cache.putIfAbsent(key, item) != null) {
            return false;
        }
        return true;
    }

    public static JSONObject get(String key) {
        CacheItem item = cache.get(key);
        if (item == null) {
            return null;
        }
        if (now() >= item.getExpire()) {
            return null;
        }
        return item.getValue();
    }

    static class CacheItem{
        private JSONObject value;
        private int expire;

        public CacheItem(JSONObject value, Integer expire) {
            this.value = value;
            this.expire = now() + expire;
        }

        public JSONObject getValue() {
            return value;
        }

        public void setValue(JSONObject value) {
            this.value = value;
        }

        public Integer getExpire() {
            return expire;
        }

        public void setExpire(Integer expire) {
            this.expire = expire;
        }
    }

    private static int now() {
        return (int)(System.currentTimeMillis() / 1000);
    }
}
