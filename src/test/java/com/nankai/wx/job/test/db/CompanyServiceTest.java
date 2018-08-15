package com.nankai.wx.job.test.db;

import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.db.service.CompanyService;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.test.TestBase;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

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

        ResultDto<Company> res = companyService.insert(company);
        System.out.println("res=" + res);
    }

    @Test
    public void update() {
        Company company = gen();
        company.setId(1);
        company.setName(null);
        company.setFullname("北京三快在线科技有限公司");
        ResultDto<Integer> res = companyService.update(company);
        Assert.assertTrue(res.getData() == 1);
    }

    @Test
    public void getCompanyByKeyword() {
        String keyword = "团";
        ResultDto<List<Company>> res= companyService.getCompanyByKeyword(keyword, null, null);
        logger.info("res=", res);
    }

    private Company gen(){
        Company company = new Company();
        company.setId(1);
        company.setName("美团网");
        company.setFullname("北京三快在线科技有限公司");
        return company;
    }

}
