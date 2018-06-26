package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/3
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class User extends Base {
    private String openid;
    private String unionid;
    private String name;
    private String avatar;

    private String email;
    private String mobile;
    private String wechat;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("openid='").append(openid).append('\'');
        sb.append(", unionid='").append(unionid).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", wechat='").append(wechat).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
