package com.nankai.wx.job.dto;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/6
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class JobQuery {
    private Integer maxId;
    private Integer limit;
    private Integer cityId;
    private Integer companyId;
    private String companyIds;
    private Integer categoryId;

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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobQuery{");
        sb.append("maxId=").append(maxId);
        sb.append(", limit=").append(limit);
        sb.append(", cityId=").append(cityId);
        sb.append(", companyId=").append(companyId);
        sb.append(", companyIds='").append(companyIds).append('\'');
        sb.append(", categoryId=").append(categoryId);
        sb.append('}');
        return sb.toString();
    }
}
