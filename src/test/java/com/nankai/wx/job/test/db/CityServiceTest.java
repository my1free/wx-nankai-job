package com.nankai.wx.job.test.db;

import com.nankai.wx.job.db.domain.City;
import com.nankai.wx.job.db.service.CityService;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.test.TestBase;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CityServiceTest extends TestBase {
    @Resource
    private CityService cityService;

    @Test
    public void insertJob() {
        City city = new City();
        city.setName("天津");
        ResultDto<Integer> res = cityService.insert(city);
        System.out.println("res=" + res);
        Assert.assertTrue(res.isSuccess());
    }
}
