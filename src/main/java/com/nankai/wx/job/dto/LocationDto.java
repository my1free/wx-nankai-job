package com.nankai.wx.job.dto;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class LocationDto {
    private Integer id;
    private String title;
    private String address;
    private String lat;
    private String lng;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LocationDto{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", lat='").append(lat).append('\'');
        sb.append(", lng='").append(lng).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
