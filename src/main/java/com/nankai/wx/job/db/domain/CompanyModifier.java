package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CompanyModifier extends Base{
    private Integer companyId;
    private Integer userId;

    public CompanyModifier() {}

    public CompanyModifier(Integer companyId, Integer userId) {
        this.companyId = companyId;
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CompanyModifier{");
        sb.append("companyId=").append(companyId);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
