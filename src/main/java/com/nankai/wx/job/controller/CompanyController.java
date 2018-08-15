package com.nankai.wx.job.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.db.domain.CompanyLocation;
import com.nankai.wx.job.dto.CompanyDto;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.service.CompanyInfoService;
import com.nankai.wx.job.util.CommonUtil;
import com.nankai.wx.job.util.HttpBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Controller
@RequestMapping("/company")
public class CompanyController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Resource
    private CompanyInfoService companyInfoService;

    @ResponseBody
    @RequestMapping("/detail")
    public JSONObject detail(String sessionId, Integer companyId) {
        logger.info("[detail] [get company detail] sessionId={}, companyId={}", sessionId, companyId);
        try {
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }

            ResultDto<CompanyDto> companyRes = companyInfoService.getCompanyDetail(openid, companyId);

            return HttpBuilder.genData(companyRes.getData());
        } catch (Exception e) {
            logger.error("[detail] [get company detail exception]", e);
            return HttpBuilder.genError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/info")
    public JSONObject info(Integer companyId) {
        logger.info("[detail] [get company info] companyId={}", companyId);
        try {
            ResultDto<CompanyDto> companyRes = companyInfoService.getCompanyInfo(companyId);

            return HttpBuilder.genData(companyRes.getData());
        } catch (Exception e) {
            logger.error("[detail] [get company info exception]", e);
            return HttpBuilder.genError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/search")
    public JSONObject search(String sessionId, String keyword, Integer maxId, Integer limit) {
        logger.info("[detail] [search company] sessionId={}, keyword={}, maxId={}, limit={}",
                sessionId, keyword, maxId, limit);
        try {
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<List<CompanyDto>> companyRes =
                    companyInfoService.searchCompany(openid, keyword, maxId, limit);

            return HttpBuilder.genData(companyRes.getData());
        } catch (Exception e) {
            logger.error("[detail] [get company detail exception]", e);
            return HttpBuilder.genError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/modify")
    public JSONObject modify(String sessionId, JSONObject params) {
        logger.info("[insert] [insert company] sessionId={}, params={}",
                sessionId, params);
        try {
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }

            Company company = parseCompany(params, "company");
            List<CompanyLocation> addedLocations = parseLocations(params, "added");
            List<CompanyLocation> modifiedLocations = parseLocations(params, "modified");
            List<CompanyLocation> removedLocations = parseLocations(params, "removed");

            ResultDto<Boolean> insertRes = companyInfoService.modifyCompany(
                    openid,
                    company,
                    addedLocations,
                    modifiedLocations,
                    removedLocations);

            return HttpBuilder.genData(insertRes.getData());
        } catch (Exception e) {
            logger.error("[detail] [insert company exception]", e);
            return HttpBuilder.genError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/insert")
    public JSONObject insert(String sessionId, JSONObject params) {
        logger.info("[insert] [insert company] sessionId={}, params={}",
                sessionId, params);
        try {
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }

            Company company = parseCompany(params, "company");
            List<CompanyLocation> locations = parseLocations(params, "locations");

            ResultDto<Boolean> insertRes =
                    companyInfoService.insertCompany(openid, company, locations);

            return HttpBuilder.genData(insertRes.getData());
        } catch (Exception e) {
            logger.error("[detail] [insert company exception]", e);
            return HttpBuilder.genError(e);
        }
    }

    private Company parseCompany(JSONObject params, String key) {
        JSONObject jsonObj = params.getJSONObject("company");
        if (jsonObj == null) {
            return null;
        }
        Company company = new Company();
        company.setName(jsonObj.getString("name"));
        company.setFullname(jsonObj.getString("fullname"));
        company.setLogo(jsonObj.getString("logo"));
        company.setIntroduction(jsonObj.getString("introduction"));
        return company;
    }

    private List<CompanyLocation> parseLocations(JSONObject params, String key) {
        JSONArray locationArray = params.getJSONArray("locations");
        if (locationArray == null || locationArray.size() == 0) {
            return Collections.emptyList();
        }
        List<CompanyLocation> companyLocations = Lists.newArrayList();
        for (int i = 0; i < locationArray.size(); i++) {
            CompanyLocation companyLocation = parseLocation(locationArray.getJSONObject(i));
            if (companyLocation != null) {
                companyLocations.add(companyLocation);
            }
        }
        return companyLocations;
    }

    private CompanyLocation parseLocation(JSONObject params) {
        if (params == null) {
            return null;
        }
        CompanyLocation companyLocation = new CompanyLocation();
        companyLocation.setAddress(params.getString("address"));
        companyLocation.setCompanyId(params.getInteger("companyId"));
        companyLocation.setLat(params.getString("lat"));
        companyLocation.setLng(params.getString("lng"));
        companyLocation.setTitle(params.getString("title"));
        companyLocation.setValid(1);
        return companyLocation;
    }
}
