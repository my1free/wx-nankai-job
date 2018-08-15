package com.nankai.wx.job.db.service;

import com.nankai.wx.job.db.dao.FeedbackDao;
import com.nankai.wx.job.db.domain.Feedback;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.NumberUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/7/8
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class FeedbackService {
    @Resource
    private FeedbackDao feedbackDao;

    public ResultDto<List<Feedback>> getAllFeedback() {
        return ResBuilder.genData(feedbackDao.getAllFeedback());
    }

    public ResultDto<Boolean> insert(Feedback feedback) {
        if (!checkFeedback(feedback)) {
            return ResBuilder.genError("invalid input");
        }
        return ResBuilder.genData(feedbackDao.insert(feedback) > 0);
    }

    private boolean checkFeedback(Feedback feedback) {
        if (feedback == null) {
            return false;
        }
        if (NumberUtil.isNullOrZero(feedback.getUserId())) {
            return false;
        }
        if (StringUtils.isBlank(feedback.getFeedback())) {
            return false;
        }

        return true;
    }
}
