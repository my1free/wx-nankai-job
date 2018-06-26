package com.nankai.wx.job.db.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.JobDao;
import com.nankai.wx.job.db.domain.Job;
import com.nankai.wx.job.dto.JobQuery;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.dto.SearchQuery;
import com.nankai.wx.job.util.CommonUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobService.class);

    private int defaultLimit = 20;

    @Resource
    private JobDao jobDao;

    public ResultDto<Integer> insert(Job job) {
        Preconditions.checkArgument(job != null);
        job.setStatus((short) 1);
        if (job.getCompanyId() == null
                || job.getCompanyId() <= 0) {
            return ResBuilder.genError("invalid companyId");
        }
        if (StringUtils.isBlank(job.getTitle())) {
            return ResBuilder.genError("blank title");
        }
        if (job.getSalaryLow() == null
                || job.getSalaryLow() <= 0
                || job.getSalaryHigh() == null
                || job.getSalaryHigh() <= 0) {
            return ResBuilder.genError("invalid salary");
        }
        if (job.getPublisher() == null
                || job.getPublisher() <= 0) {
            return ResBuilder.genError("invalid publisher");
        }
        jobDao.insert(job);
        return ResBuilder.genData(job.getId());
    }

    /**
     * 获取工作列表
     *
     * @param maxId
     * @param limit
     * @return
     */
    public ResultDto<List<Job>> getJobList(Integer maxId, Integer limit) {
        if (maxId == null || maxId <= 0) {
            maxId = Integer.MAX_VALUE;
        }
        if (limit == null || limit <= 0) {
            limit = defaultLimit;
        }
        return ResBuilder.genData(jobDao.getJobList(maxId, limit));
    }

    public ResultDto<List<Job>> getJobByQuery(JobQuery query) {
        Map<String, Object> conds = CommonUtil.convertQuery2Conds(query);

        return ResBuilder.genData(jobDao.getJobByConds(conds));
    }

    public ResultDto<List<Job>> getJobBySearch(SearchQuery query) {

        Map<String, Object> conds = CommonUtil.convertSearchQuery2Conds(query);
        if (StringUtils.isBlank((String) conds.get("keyword"))) {
            return ResBuilder.genError("keyword should not be blank");
        }

        return ResBuilder.genData(jobDao.getJobBySearch(conds));
    }

    public ResultDto<Job> getJobById(Integer jobId) {
        if (jobId == null || jobId <= 0) {
            return ResBuilder.genError("invalid jobId");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("id", jobId);
        List<Job> jobs = jobDao.getJobByConds(conds);
        if (CollectionUtils.isEmpty(jobs)) {
            return ResBuilder.genData(null);
        }
        return ResBuilder.genData(jobs.get(0));
    }

    public ResultDto<List<Job>> getJobsByIds(Set<Integer> jobIds) {
        if (CollectionUtils.isEmpty(jobIds)) {
            return ResBuilder.genData(Collections.emptyList());
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("ids", jobIds);
        return ResBuilder.genData(jobDao.getJobByConds(conds));
    }

    public ResultDto<Map<Integer, Job>> getJobMapByIds(Set<Integer> jobIds) {
        ResultDto<List<Job>> jobRes = getJobsByIds(jobIds);
        Map<Integer, Job> map = Maps.newHashMap();
        jobRes.getData().forEach(job -> map.put(job.getId(), job));
        return ResBuilder.genData(map);
    }

}
