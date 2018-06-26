package com.nankai.wx.job.dto;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/6
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class SearchQuery {
    private Integer maxId;
    private Integer limit;
    private String keyword;
    private Integer cityId;

    public Integer getMaxId() {
        return maxId;
    }

    public void setMaxId(Integer maxId) {
        this.maxId = maxId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SearchQuery{");
        sb.append("maxId=").append(maxId);
        sb.append(", limit=").append(limit);
        sb.append(", keyword=").append(keyword);
        sb.append(", cityId=").append(cityId);
        sb.append('}');
        return sb.toString();
    }
}
