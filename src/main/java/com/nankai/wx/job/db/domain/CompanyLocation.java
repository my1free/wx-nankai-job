package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/7/31
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CompanyLocation extends Base{
    private Integer companyId;
    private String title;
    private String address;
    private String lat;
    private String lng;
    private Integer valid;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CompanyLocation{");
        sb.append("companyId=").append(companyId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", lat='").append(lat).append('\'');
        sb.append(", lng='").append(lng).append('\'');
        sb.append(", valid=").append(valid);
        sb.append('}');
        return sb.toString();
    }
}
