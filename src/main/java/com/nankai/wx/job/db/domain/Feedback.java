package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/7/8
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class Feedback extends Base{
    private Integer userId;
    private String feedback;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Feedback{");
        sb.append("userId=").append(userId);
        sb.append(", feedback='").append(feedback).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
