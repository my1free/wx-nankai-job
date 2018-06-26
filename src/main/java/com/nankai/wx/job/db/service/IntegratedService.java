package com.nankai.wx.job.db.service;

import com.nankai.wx.job.db.dao.IntegratedDao;
import com.nankai.wx.job.db.domain.Job;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.ResBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class IntegratedService {

    private static final Logger logger = LoggerFactory.getLogger(IntegratedService.class);


    @Resource
    private IntegratedDao integratedDao;

    private int defaultLimit = 20;


    /**
     * 获取工作列表
     *
     * @param maxId
     * @param limit
     * @return
     */
    public ResultDto<List<Job>> getConcernedJobList(Integer userId, Integer maxId, Integer limit) {
        if (maxId == null || maxId <= 0) {
            maxId = Integer.MAX_VALUE;
        }
        if (limit == null || limit <= 0) {
            limit = defaultLimit;
        }

        return ResBuilder.genData(integratedDao.getConcernedJobList(userId, maxId, limit));
    }

}
