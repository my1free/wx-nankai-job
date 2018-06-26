package com.nankai.wx.job.db.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.UserDao;
import com.nankai.wx.job.db.domain.User;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.HttpUtil;
import com.nankai.wx.job.util.NumberUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String appId = "wxe3f2f36da6b9e696";
    private static final String secret = "7717310a8e96d174cac4cf216cb5d09d";

    private static final String wechatApiHost = "https://api.weixin.qq.com/";
    private static String code2SessionUri = "sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    @Resource
    private UserDao userDao;

    /**
     * 插入新的用户或者更新用户
     *
     * @param openid
     * @param name
     * @param avatar
     * @return
     */
    public ResultDto<User> updateOrInsertUser(String openid, String name, String avatar) {
        if (StringUtils.isBlank(openid)) {
            return ResBuilder.genError("invalid parameters");
        }
        User user = new User();
        user.setOpenid(openid);
        if (StringUtils.isNotBlank(name)) {
            user.setName(name);
        }
        if (StringUtils.isNotBlank(avatar)) {
            user.setAvatar(avatar);
        }
        userDao.insertOrUpdate(user);

        return ResBuilder.genData(user);
    }

    /**
     * 根据openid获取User信息，如果没有，则插入一条
     * @param openid
     * @return
     */
    public ResultDto<User> getOrInsert(String openid) {
        if (StringUtils.isBlank(openid)) {
            return ResBuilder.genError("invalid parameters");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("openid", openid);
        List<User> users = userDao.getUserByConds(conds);
        if (CollectionUtils.isNotEmpty(users)) {
            return ResBuilder.genData(users.get(0));
        } else {
            return ResBuilder.genData(updateOrInsertUser(openid, null, null));
        }
    }

    public ResultDto<Integer> getUserId(String openid) {
        if (StringUtils.isBlank(openid)) {
            return ResBuilder.genError("invalid parameters");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("openid", openid);
        List<User> users = userDao.getUserByConds(conds);
        if (CollectionUtils.isNotEmpty(users)) {
            return ResBuilder.genData(users.get(0));
        }
        return ResBuilder.genData(null);
    }

    public ResultDto<List<User>> getAllUsers() {
        return ResBuilder.genData(userDao.getAllUsers());
    }

    public ResultDto<String> onLogin(String code) {
        Preconditions.checkArgument(StringUtils.isNotBlank(code));
        String url = wechatApiHost +
                code2SessionUri.replace("APPID", appId)
                        .replace("SECRET", secret)
                        .replace("JSCODE", code);
        String res = HttpUtil.doGet(url);
        JSONObject data = JSONObject.parseObject(res);
        if (data.getInteger("errcode") != null) {
            return ResBuilder.genError(data.getString("errmsg"));
        }
        String openid = data.getString("openid");
        String sessionKey = data.getString("session_key");

        return ResBuilder.genData(res);
    }

    public ResultDto<User> getUserById(Integer userId) {
        if (NumberUtil.isNullOrZero(userId)) {
            return ResBuilder.genError("invalid user");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("id", userId);
        List<User> users = userDao.getUserByConds(conds);
        if (CollectionUtils.isEmpty(users)) {
            return ResBuilder.genError("invalid user");
        }
        return ResBuilder.genData(users.get(0));
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public ResultDto<Boolean> updateUserInfo(User user) {
        Preconditions.checkArgument(user != null
                && !NumberUtil.isNullOrZero(user.getId()));
        int num = userDao.updateUser(user);
        return ResBuilder.genData(num > 0);
    }
}
