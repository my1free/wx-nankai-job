package com.nankai.wx.job.test.db;

import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.db.service.CompanyService;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.test.TestBase;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CompanyServiceTest extends TestBase {
    @Resource
    private CompanyService companyService;

    @Test
    public void insertCompany() {
        Company company = new Company();
        company.setName("腾讯");
        company.setLogo("http://opopjydml.bkt.clouddn.com/tencent-logo.png");

        ResultDto<Integer> res = companyService.insert(company);
        System.out.println("res=" + res);
    }

}
