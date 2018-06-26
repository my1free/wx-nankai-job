package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/15
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class Concerned extends Base{
    private Integer userId;
    private Integer companyId;
    private Integer valid;

    public Concerned(){}

    public Concerned(Integer userId, Integer companyId, Integer valid) {
        this.userId = userId;
        this.companyId = companyId;
        this.valid = valid;
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
        final StringBuffer sb = new StringBuffer("Concerned{");
        sb.append("userId=").append(userId);
        sb.append(", companyId=").append(companyId);
        sb.append(", valid=").append(valid);
        sb.append('}');
        return sb.toString();
    }
}
