package com.nankai.wx.job.controller;

import com.alibaba.fastjson.JSONObject;
import com.nankai.wx.job.dto.JobDetailDto;
import com.nankai.wx.job.dto.JobInfoDto;
import com.nankai.wx.job.dto.JobQuery;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.dto.SearchQuery;
import com.nankai.wx.job.service.JobInfoService;
import com.nankai.wx.job.util.CommonUtil;
import com.nankai.wx.job.util.HttpBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Controller
@RequestMapping("/job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Resource
    private JobInfoService jobInfoService;

    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(JobQuery query) {
        logger.info("[list] query={}", query);
        try {
            ResultDto<List<JobInfoDto>> jobListRes = jobInfoService.getJobListByQuery(query);
            if (!jobListRes.isSuccess()) {
                logger.warn("[list] [get job list fail] res={}", jobListRes);
            }
            return HttpBuilder.genJson(jobListRes);
        } catch (Exception e) {
            logger.error("[list] [get job list exception]", e);
            return HttpBuilder.genError(e);
        }
    }


    @ResponseBody
    @RequestMapping("/detail")
    public JSONObject detail(Integer jobId, String sessionId) {
        logger.error("[detail] jobId={}, sessionId={}", jobId, sessionId);
        try {
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<JobDetailDto> jobDetailRes = jobInfoService.jobDetail(jobId, openid);
            if (!jobDetailRes.isSuccess()) {
                logger.warn("[detail] [get job detail fail] res={}", jobDetailRes);
            }
            return HttpBuilder.genJson(jobDetailRes);
        } catch (Exception e) {
            logger.error("[detail] [get job detail exception]", e);
            return HttpBuilder.genError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/search")
    public JSONObject search(SearchQuery query) {
        logger.error("[search] searchQuery={}", query);
        try {
            ResultDto<List<JobInfoDto>> jobListRes = jobInfoService.getJobBySearch(query);
            if (!jobListRes.isSuccess()) {
                logger.warn("[search] [get job list fail] res={}", jobListRes);
            }
            return HttpBuilder.genJson(jobListRes);
        } catch (Exception e) {
            logger.error("[search] [get job list exception]", e);
            return HttpBuilder.genError(e);
        }
    }
}
