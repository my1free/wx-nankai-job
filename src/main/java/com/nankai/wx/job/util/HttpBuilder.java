package com.nankai.wx.job.util;

import com.alibaba.fastjson.JSONObject;
import com.nankai.wx.job.dto.ResultDto;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class HttpBuilder {

    public static JSONObject genJson(ResultDto dto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", dto.isSuccess() ? 0 : 1);
        jsonObject.put("msg", dto.getMsg());
        jsonObject.put("data", dto.getData());
        return jsonObject;
    }

    public static JSONObject genCode(int code, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    public static JSONObject genData(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        jsonObject.put("data", data);
        return jsonObject;
    }

    public static JSONObject genError(Exception e) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("msg", e.getMessage());
        jsonObject.put("data", null);
        return jsonObject;
    }
}
