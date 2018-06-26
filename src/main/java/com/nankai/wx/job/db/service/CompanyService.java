package com.nankai.wx.job.db.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.CompanyDao;
import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.CommonUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Resource
    private CompanyDao companyDao;

    public ResultDto<Integer> insert(Company company) {
        Preconditions.checkArgument(company != null);
        if (StringUtils.isBlank(company.getName())) {
            return ResBuilder.genError("invalid name");
        }
        companyDao.insert(company);
        return ResBuilder.genData(company.getId());
    }

    public ResultDto<Company> getCompanyById(Integer id) {
        if (id == null || id <= 0) {
            return ResBuilder.genError("invalid id");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("id", id);
        List<Company> companies = companyDao.getCompanyByConds(conds);
        if (CollectionUtils.isEmpty(companies)) {
            return ResBuilder.genData(null);
        }
        return ResBuilder.genData(companies.get(0));
    }

    public ResultDto<Map<Integer, Company>> getCompanyMapByIds(Set<Integer> ids) {
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("ids", ids);
        List<Company> companies = companyDao.getCompanyByConds(conds);
        return ResBuilder.genData(CommonUtil.convertList2Map(companies));
    }

}
