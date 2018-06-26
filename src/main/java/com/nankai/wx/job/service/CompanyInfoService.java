package com.nankai.wx.job.service;

import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.db.domain.Concerned;
import com.nankai.wx.job.db.domain.User;
import com.nankai.wx.job.db.service.CompanyService;
import com.nankai.wx.job.db.service.ConcernedService;
import com.nankai.wx.job.db.service.UserService;
import com.nankai.wx.job.dto.CompanyDto;
import com.nankai.wx.job.dto.JobInfoDto;
import com.nankai.wx.job.dto.JobQuery;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.ResBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
public class CompanyInfoService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyInfoService.class);

    @Resource
    private CompanyService companyService;
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private UserService userService;
    @Resource
    private ConcernedService concernedService;

    public ResultDto<CompanyDto> getCompanyById(String openid, Integer companyId) {
        ResultDto<Company> companyRes = companyService.getCompanyById(companyId);
        if (!companyRes.isSuccess() || companyRes.getData() == null) {
            return ResBuilder.genSuccess("no company");
        }

        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }


        CompanyDto companyDto = fillCompany(companyRes.getData(), userRes.getData().getId());

        JobQuery query = new JobQuery();
        query.setCompanyId(companyId);

        ResultDto<List<JobInfoDto>> jobRes = jobInfoService.getJobListByQuery(query);
        if (!jobRes.isSuccess()) {
            return ResBuilder.genError(jobRes.getMsg());
        }
        companyDto.setJobInfoDtos(jobRes.getData());
        return ResBuilder.genData(companyDto);
    }

    public CompanyDto fillCompany(Company company, int userId) {
        CompanyDto companyDto = new CompanyDto();
        BeanUtils.copyProperties(company, companyDto);

        ResultDto<Concerned> concernedRes = concernedService.getConcerned(userId, company.getId());
        if (concernedRes.isSuccess() && concernedRes.getData() != null) {
            companyDto.setConcerned(concernedRes.getData().getValid() == 1);
        }
        return companyDto;
    }
}
