package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CompanyBelonged extends Base{
    private Integer userId;
    private Integer companyId;
    private Integer valid;

    public CompanyBelonged() {}

    public CompanyBelonged(Integer userId, Integer companyId) {
        this.userId = userId;
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CompanyBelonged{");
        sb.append("userId=").append(userId);
        sb.append(", companyId=").append(companyId);
        sb.append(", valid=").append(valid);
        sb.append('}');
        return sb.toString();
    }
}
