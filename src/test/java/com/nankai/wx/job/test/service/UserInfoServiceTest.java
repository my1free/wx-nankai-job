package com.nankai.wx.job.test.service;

import com.nankai.wx.job.service.UserInfoService;
import com.nankai.wx.job.test.TestBase;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class UserInfoServiceTest extends TestBase {
    @Resource
    private UserInfoService userInfoService;

    @Test
    public void updateOrInsertUser() {
        String openid = "f9b43e68d683cb9448717e5eae8cb5d31526016876";
        String name = "MY";
        String avatar = "https://wx.qlogo.cn/mmopen/vi_32/L6TxyQ8kYHpBcMgabVjXZYVQTVIj9gC5lOyaof28gVfjicwuxorrfWDkd8pw6I6LPuuv8LTnMOiawfve6crAjtqA/132";
//        ResultDto<Integer> jobRes = userInfoService.updateOrInsertUser(openid, name, avatar);
//        System.out.println("res=" + jobRes);
    }

}
