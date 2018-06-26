package com.nankai.wx.job.controller;

import com.alibaba.fastjson.JSONObject;
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
        logger.error("[detail] [get company detail] sessionId={}, companyId={}", sessionId, companyId);
        try {
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }

            ResultDto<CompanyDto> companyRes = companyInfoService.getCompanyById(openid, companyId);

            return HttpBuilder.genData(companyRes.getData());
        } catch (Exception e) {
            logger.error("[detail] [get company detail exception]", e);
            return HttpBuilder.genError(e);
        }
    }

}
