package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class Company extends Base {
    private String name;
    private String logo;
    private String location;
    private String compayAbstract;
    private String description;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompayAbstract() {
        return compayAbstract;
    }

    public void setCompayAbstract(String compayAbstract) {
        this.compayAbstract = compayAbstract;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Company{");
        sb.append("name='").append(name).append('\'');
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", compayAbstract='").append(compayAbstract).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
