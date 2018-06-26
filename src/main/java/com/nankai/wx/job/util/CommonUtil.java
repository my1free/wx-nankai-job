package com.nankai.wx.job.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nankai.wx.job.db.domain.Base;
import com.nankai.wx.job.db.domain.City;
import com.nankai.wx.job.db.domain.Collection;
import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.db.domain.Job;
import com.nankai.wx.job.dto.JobDetailDto;
import com.nankai.wx.job.dto.JobInfoDto;
import com.nankai.wx.job.dto.JobQuery;
import com.nankai.wx.job.dto.SearchQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CommonUtil {

    public static String getOpenid(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return "";
        }
        JSONObject value = CacheUtil.get(sessionId);
        if (value == null || StringUtils.isBlank(value.getString("openid"))) {
            return "";
        }
        return value.getString("openid");
    }

    public static boolean checkSession(String sessionId) {
        String openid = CommonUtil.getOpenid(sessionId);
        if (StringUtils.isBlank(openid)) {
            return false;
        }
        return true;
    }

    /**
     * 从一个List元素中提取其id
     *
     * @param lists
     * @return
     */
    public static Set<Integer> getIdSet(List<? extends Base> lists) {
        if (CollectionUtils.isEmpty(lists)) {
            return Collections.emptySet();
        }
        Set<Integer> ids = Sets.newHashSet();
        lists.forEach(ele -> ids.add(ele.getId()));
        return ids;
    }

    /**
     * 将list转换成map
     *
     * @param lists
     * @return
     */
    public static Map convertList2Map(List<? extends Base> lists) {
        if (CollectionUtils.isEmpty(lists)) {
            return Collections.emptyMap();
        }
        Map<Integer, Base> map = Maps.newHashMap();
        lists.forEach(ele -> map.put(ele.getId(), ele));
        return map;
    }

    /**
     * 从job list中提取company id
     *
     * @param jobs
     * @return
     */
    public static Set<Integer> getCompanyIdsFromJobs(List<Job> jobs) {
        if (CollectionUtils.isEmpty(jobs)) {
            return Collections.emptySet();
        }

        Set<Integer> ids = Sets.newHashSet();
        jobs.forEach(item -> ids.add(item.getCompanyId()));
        return ids;
    }

    /**
     * 从job list中提取city id
     *
     * @param jobs
     * @return
     */
    public static Set<Integer> getCityIdsFromJobs(List<Job> jobs) {
        if (CollectionUtils.isEmpty(jobs)) {
            return Collections.emptySet();
        }

        Set<Integer> ids = Sets.newHashSet();
        jobs.forEach(item -> ids.add(item.getCityId()));
        return ids;
    }

    public static JobDetailDto integrateJobDetail(Job job,
                                                  Company company,
                                                  City city,
                                                  Collection collection) {
        if (job == null) {
            return null;
        }

        JobDetailDto jobDetailDto = new JobDetailDto();
        BeanUtils.copyProperties(job, jobDetailDto);
        if (StringUtils.isNotBlank(job.getJobAbstract())) {
            JSONObject jobAbstract = JSONObject.parseObject(job.getJobAbstract());
            jobDetailDto.setEducation(jobAbstract.getString("education"));
            jobDetailDto.setExpirence(jobAbstract.getString("expirence"));
        }

        if (company != null) {
            jobDetailDto.setCompanyName(company.getName());
            jobDetailDto.setCompanyLogo(company.getLogo());
        }

        if (city != null) {
            jobDetailDto.setCityName(city.getName());
        }

        if (collection != null) {
            jobDetailDto.setCollected(collection.getValid() == 1);
        } else {
            jobDetailDto.setCollected(false);
        }

        return jobDetailDto;
    }

    /**
     * 将job信息、company信息、city信息等整合成前端所需数据
     *
     * @param jobs
     * @param companyMap
     * @param cityMap
     * @return
     */
    public static List<JobInfoDto> integrateJobInfo(List<Job> jobs,
                                                    Map<Integer, Company> companyMap,
                                                    Map<Integer, City> cityMap) {
        if (CollectionUtils.isEmpty(jobs)) {
            return Collections.emptyList();
        }

        List<JobInfoDto> jobInfoDtos = Lists.newArrayList();
        jobs.forEach(job -> {
            JobInfoDto jobInfoDto = new JobInfoDto();
            BeanUtils.copyProperties(job, jobInfoDto);
            if (StringUtils.isNotBlank(job.getJobAbstract())) {
                JSONObject jobAbstract = JSONObject.parseObject(job.getJobAbstract());
                jobInfoDto.setEducation(jobAbstract.getString("education"));
                jobInfoDto.setExpirence(jobAbstract.getString("expirence"));
            }
            Company company = companyMap.get(job.getCompanyId());
            if (company != null) {
                jobInfoDto.setCompanyName(company.getName());
                jobInfoDto.setCompanyLogo(company.getLogo());
            }
            City city = cityMap.get(job.getCityId());
            if (city != null) {
                jobInfoDto.setCityName(city.getName());
            }
            jobInfoDtos.add(jobInfoDto);
        });
        return jobInfoDtos;
    }

    public static Map<String, Object> convertQuery2Conds(JobQuery query) {
        Preconditions.checkArgument(query != null, "query is null");
        Map<String, Object> conds = Maps.newHashMap();
        if (query.getMaxId() == null || query.getMaxId() <= 0) {
            conds.put("maxId", Integer.MAX_VALUE);
        } else {
            conds.put("maxId", query.getMaxId());
        }
        if (query.getLimit() == null || query.getLimit() <= 0) {
            conds.put("limit", 10);
        } else {
            conds.put("limit", query.getLimit());
        }

        if (query.getCityId() != null && query.getCityId() > 0) {
            conds.put("cityId", query.getCityId());
        }
        if (query.getCompanyId() != null && query.getCompanyId() > 0) {
            conds.put("companyId", query.getCompanyId());
        }
        if (query.getCategoryId() != null && query.getCategoryId() > 0) {
            conds.put("categoryId", query.getCategoryId());
        }

        if (StringUtils.isNotBlank(query.getCompanyIds())) {
            conds.put("companyIdsStr", query.getCompanyIds());
        }

        return conds;
    }

    public static Map<String, Object> convertSearchQuery2Conds(SearchQuery query) {
        Preconditions.checkArgument(query != null, "query is null");
        Map<String, Object> conds = Maps.newHashMap();
        if (query.getMaxId() == null || query.getMaxId() <= 0) {
            conds.put("maxId", Integer.MAX_VALUE);
        } else {
            conds.put("maxId", query.getMaxId());
        }
        if (query.getLimit() == null || query.getLimit() <= 0) {
            conds.put("limit", 10);
        } else {
            conds.put("limit", query.getLimit());
        }

        if (query.getCityId() != null && query.getCityId() > 0) {
            conds.put("cityId", query.getCityId());
        }
        conds.put("keyword", query.getKeyword());
        return conds;
    }
}
