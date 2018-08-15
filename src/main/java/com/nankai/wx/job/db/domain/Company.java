package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class Company extends Base {
    private String name;
    private String fullname;
    private String logo;
    private String introduction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Company{");
        sb.append("name='").append(name).append('\'');
        sb.append(", fullname='").append(fullname).append('\'');
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
