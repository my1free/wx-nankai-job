package com.nankai.wx.job.test.db;

import com.nankai.wx.job.db.domain.CompanyModifier;
import com.nankai.wx.job.db.service.ModifierService;
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
public class ModifierServiceTest extends TestBase {
    @Resource
    private ModifierService modifierService;

    @Test
    public void insertCompanyModifier() {
        CompanyModifier modifier = gen();
        ResultDto<CompanyModifier> res = modifierService.insertCompanyModifier(modifier);
        Assert.assertTrue(res.isSuccess());
    }

    private CompanyModifier gen() {
        CompanyModifier modifier = new CompanyModifier();
        modifier.setCompanyId(1);
        modifier.setUserId(1);
        return modifier;
    }
}
