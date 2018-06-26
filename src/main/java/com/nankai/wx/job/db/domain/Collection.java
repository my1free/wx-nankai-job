package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/15
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class Collection extends Base {
    private Integer userId;
    private Integer jobId;
    private Integer valid;

    public Collection() {
        //
    }

    public Collection(Integer userId, Integer jobId, Integer valid) {
        this.userId = userId;
        this.jobId = jobId;
        this.valid = valid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Collection{");
        sb.append("userId=").append(userId);
        sb.append(", jobId=").append(jobId);
        sb.append(", valid=").append(valid);
        sb.append('}');
        return sb.toString();
    }
}
