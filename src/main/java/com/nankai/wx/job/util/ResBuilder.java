package com.nankai.wx.job.util;

import com.nankai.wx.job.dto.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class ResBuilder {

    private static final Logger logger = LoggerFactory.getLogger("RESULT");

    public static ResultDto genSuccess() {
        ResultDto dto = new ResultDto();
        dto.setSuccess(true);
        dto.setMsg("success");
        return dto;
    }

    public static ResultDto genSuccess(String msg) {
        ResultDto dto = genSuccess();
        dto.setMsg(msg);
        return dto;
    }

    public static ResultDto genError(String msg) {
        ResultDto dto = new ResultDto();
        dto.setSuccess(false);
        dto.setMsg(msg);
        logger.error("[ResultDto] [gen error] msg={}", msg);
        return dto;
    }

    public static ResultDto genData(Object data) {
        ResultDto dto = genSuccess();
        dto.setData(data);
        return dto;
    }

    public static ResultDto genData(Object data, String msg) {
        ResultDto dto = genSuccess(msg);
        dto.setData(data);
        return dto;
    }

}
