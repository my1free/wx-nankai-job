package com.nankai.wx.job.test.db;

import com.nankai.wx.job.db.domain.CompanyLocation;
import com.nankai.wx.job.db.service.LocationService;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.test.TestBase;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class LocationServiceTest extends TestBase {
    @Resource
    private LocationService locationService;

    @Test
    public void getCompanyLocations() {
        int companyId = 1;
        ResultDto<List<CompanyLocation>> res = locationService.getCompanyLocations(companyId);
        logger.info("res=", res.getData());
    }

    @Test
    public void insertCompanyLocation() {
        CompanyLocation location = new CompanyLocation();
        location.setCompanyId(1);
        location.setTitle("天安门广场");
        location.setAddress("北京市东城区东长安街");
        location.setLat("39.90323");
        location.setLng("116.39772");

        ResultDto<CompanyLocation> res = locationService.insertCompanyLocation(location);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void updateCompanyLocation() {
        CompanyLocation location = new CompanyLocation();
        location.setCompanyId(1);
        location.setId(1);
        location.setTitle("天安门广场1");
//        location.setAddress("北京市东城区东长安街");
//        location.setLat("39.90323");
//        location.setLng("116.39772");

        ResultDto<CompanyLocation> res = locationService.updateCompanyLocation(location);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void removeCompanyLocation(){
        CompanyLocation location = new CompanyLocation();
        location.setId(1);

        ResultDto<Integer> res = locationService.removeCompanyLocation(location);
        Assert.assertTrue(res.isSuccess() && res.getData() == 1);
    }

}
