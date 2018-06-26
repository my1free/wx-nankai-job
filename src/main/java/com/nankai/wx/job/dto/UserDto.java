package com.nankai.wx.job.dto;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/20
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class UserDto {
    private Integer id;
    private String name;
    private String avatar;

    private String email;
    private String mobile;
    private String wechat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        final StringBuffer sb = new StringBuffer("UserDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", wechat='").append(wechat).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
