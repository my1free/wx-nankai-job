package com.nankai.wx.job.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/11
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class SessionIdUtil {

    /**
     * 生成sessionId
     * <p>code的md5值 + unix时间戳</p>
     * @param code
     * @return
     */
    public static String genSessionId(String code){
        String encodeStr= DigestUtils.md5Hex(code);
        return String.format("%s%d", encodeStr, System.currentTimeMillis() / 1000);
    }
}
