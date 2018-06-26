package com.nankai.wx.job.test.service;

import com.nankai.wx.job.dto.JobInfoDto;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.service.JobInfoService;
import com.nankai.wx.job.test.TestBase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class JobInfoServiceTest extends TestBase {
    @Resource
    private JobInfoService jobInfoService;

    @Test
    public void getJobList() {
        ResultDto<List<JobInfoDto>> jobRes = jobInfoService.getJobList(null, null);
        System.out.println("res=" + jobRes);
    }

}
