package com.nankai.wx.job.dto;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class JobDetailDto extends JobInfoDto{
    //Job信息
    private String description;

    //Company信息
    private String locationDesc;

    //发布者信息

    //订阅者信息
    private Boolean collected;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobDetailDto{");
        sb.append("description='").append(description).append('\'');
        sb.append(", locationDesc='").append(locationDesc).append('\'');
        sb.append(", collected=").append(collected);
        sb.append('}');
        return sb.toString();
    }
}
