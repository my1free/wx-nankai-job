package com.nankai.wx.job.test.db;

import com.nankai.wx.job.db.domain.CompanyBelonged;
import com.nankai.wx.job.db.service.BelongedService;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.test.TestBase;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class BelongedServiceTest extends TestBase {
    @Resource
    private BelongedService belongedService;

    @Test
    public void insert() {
        CompanyBelonged belonged = gen();
        ResultDto<CompanyBelonged> res = belongedService.insert(belonged);
        Assert.assertTrue(res.isSuccess());
    }

    private CompanyBelonged gen() {
        CompanyBelonged belonged = new CompanyBelonged();
        belonged.setCompanyId(2);
        belonged.setUserId(2);
        return belonged;
    }
}
