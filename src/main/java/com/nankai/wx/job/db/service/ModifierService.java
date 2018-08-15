package com.nankai.wx.job.db.service;

import com.nankai.wx.job.db.dao.CompanyModifierDao;
import com.nankai.wx.job.db.domain.CompanyModifier;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.NumberUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class ModifierService {
    private static final Logger logger = LoggerFactory.getLogger(ModifierService.class);

    private static String DB_ERROR = "数据库错误";

    @Resource
    private CompanyModifierDao companyModifierDao;

    public ResultDto<CompanyModifier> insertCompanyModifier(CompanyModifier modifier) {
        try {
            ResultDto<CompanyModifier> checkRes = checkModifier(modifier);
            if (!checkRes.isSuccess()) {
                return checkRes;
            }
            companyModifierDao.insert(modifier);
            return ResBuilder.genData(modifier);
        } catch (Exception e) {
            logger.error("db exception", e);
            return ResBuilder.genError(DB_ERROR);
        }
    }

    private ResultDto<CompanyModifier> checkModifier(CompanyModifier modifier) {
        if (modifier == null) {
            return ResBuilder.genError("invalid parameters");
        }
        if (NumberUtil.isNullOrNonePositive(modifier.getCompanyId())) {
            return ResBuilder.genError("invalid company id");
        }
        if (NumberUtil.isNullOrNonePositive(modifier.getUserId())) {
            return ResBuilder.genError("invalid user id");
        }
        return ResBuilder.genData(null);
    }
}
