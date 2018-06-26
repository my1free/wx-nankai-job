package com.nankai.wx.job.service;

import com.google.common.base.Preconditions;
import com.nankai.wx.job.db.domain.City;
import com.nankai.wx.job.db.domain.Collection;
import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.db.domain.Job;
import com.nankai.wx.job.db.domain.User;
import com.nankai.wx.job.db.service.CityService;
import com.nankai.wx.job.db.service.CollectionService;
import com.nankai.wx.job.db.service.CompanyService;
import com.nankai.wx.job.db.service.JobService;
import com.nankai.wx.job.db.service.UserService;
import com.nankai.wx.job.dto.JobDetailDto;
import com.nankai.wx.job.dto.JobInfoDto;
import com.nankai.wx.job.dto.JobQuery;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.dto.SearchQuery;
import com.nankai.wx.job.util.CommonUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class JobInfoService {

    private static final Logger logger = LoggerFactory.getLogger(JobInfoService.class);

    private int defaultLimit = 20;

    @Resource
    private JobService jobService;
    @Resource
    private CompanyService companyService;
    @Resource
    private CityService cityService;
    @Resource
    private UserService userService;
    @Resource
    private CollectionService collectionService;

    public ResultDto<List<JobInfoDto>> getJobListByQuery(JobQuery query) {
        Preconditions.checkArgument(query != null, "invalid query");

        ResultDto<List<Job>> jobRes = jobService.getJobByQuery(query);
        if (!jobRes.isSuccess()) {
            return ResBuilder.genError(jobRes.getMsg());
        }
        if (CollectionUtils.isEmpty(jobRes.getData())) {
            return ResBuilder.genData(Collections.emptyList());
        }

        List<Job> jobs = jobRes.getData();

        return fillJobInfo(jobs);
    }

    public ResultDto<List<JobInfoDto>> getJobBySearch(SearchQuery query) {
        ResultDto<List<Job>> jobRes = jobService.getJobBySearch(query);
        if (!jobRes.isSuccess()) {
            return ResBuilder.genError(jobRes.getMsg());
        }
        if (CollectionUtils.isEmpty(jobRes.getData())) {
            return ResBuilder.genData(Collections.emptyList());
        }

        List<Job> jobs = jobRes.getData();
        return fillJobInfo(jobs);
    }

    public ResultDto<List<JobInfoDto>> fillJobInfo(List<Job> jobs) {
        //获取公司信息
        Set<Integer> companyIds = CommonUtil.getCompanyIdsFromJobs(jobs);
        ResultDto<Map<Integer, Company>> companyMapRes = companyService.getCompanyMapByIds(companyIds);
        Map<Integer, Company> companyMap = companyMapRes.getData();

        //获取城市信息
        Set<Integer> cityIds = CommonUtil.getCityIdsFromJobs(jobs);
        ResultDto<Map<Integer, City>> cityMapRes = cityService.getCitiesByIds(cityIds);
        Map<Integer, City> cityMap = cityMapRes.getData();

        List<JobInfoDto> jobInfoDtos = CommonUtil.integrateJobInfo(jobs, companyMap, cityMap);
        return ResBuilder.genData(jobInfoDtos);
    }

    /**
     * 获取工作列表
     *
     * @param maxId
     * @param limit
     * @return
     */
    public ResultDto<List<JobInfoDto>> getJobList(Integer maxId, Integer limit) {

        ResultDto<List<Job>> jobRes = jobService.getJobList(maxId, limit);
        if (!jobRes.isSuccess()) {
            return ResBuilder.genError(jobRes.getMsg());
        }
        if (CollectionUtils.isEmpty(jobRes.getData())) {
            return ResBuilder.genData(Collections.emptyList());
        }

        List<Job> jobs = jobRes.getData();

        //获取公司信息
        Set<Integer> companyIds = CommonUtil.getCompanyIdsFromJobs(jobs);
        ResultDto<Map<Integer, Company>> companyMapRes = companyService.getCompanyMapByIds(companyIds);
        Map<Integer, Company> companyMap = companyMapRes.getData();

        //获取城市信息
        Set<Integer> cityIds = CommonUtil.getCityIdsFromJobs(jobs);
        ResultDto<Map<Integer, City>> cityMapRes = cityService.getCitiesByIds(cityIds);
        Map<Integer, City> cityMap = cityMapRes.getData();

        List<JobInfoDto> jobInfoDtos = CommonUtil.integrateJobInfo(jobs, companyMap, cityMap);

        return ResBuilder.genData(jobInfoDtos);
    }

    public ResultDto<JobDetailDto> jobDetail(Integer jobId, String openId) {
        ResultDto<Job> jobRes = jobService.getJobById(jobId);
        if (!jobRes.isSuccess()) {
            return ResBuilder.genError(jobRes.getMsg());
        }
        Job job = jobRes.getData();

        //获取公司信息
        ResultDto<Company> companyRes = companyService.getCompanyById(job.getCompanyId());
        if (!companyRes.isSuccess()) {
            return ResBuilder.genError(companyRes.getMsg());
        }
        Company company = companyRes.getData();

        //获取城市信息
        ResultDto<City> cityRes = cityService.getCityById(job.getCityId());
        if (!cityRes.isSuccess()) {
            return ResBuilder.genError(cityRes.getMsg());
        }
        City city = cityRes.getData();

        //获取发布者信息

        //阅览人信息
        ResultDto<User> userRes = userService.getOrInsert(openId);
        if (!userRes.isSuccess()) {
            return ResBuilder.genError(userRes.getMsg());
        }
        Integer userId = userRes.getData().getId();
        ResultDto<Collection> collectionRes = collectionService.getCollection(userId, jobId);
        Collection collection = collectionRes.getData();

        JobDetailDto jobDetailDto = CommonUtil.integrateJobDetail(job, company, city, collection);

        return ResBuilder.genData(jobDetailDto);
    }

}
