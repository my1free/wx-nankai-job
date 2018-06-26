package com.nankai.wx.job.test.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nankai.wx.job.db.domain.Job;
import com.nankai.wx.job.db.service.JobService;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.dto.SearchQuery;
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
public class JobServiceTest extends TestBase {
    @Resource
    private JobService jobService;

    @Test
    public void getJobList() {
        ResultDto<List<Job>> jobRes = jobService.getJobList(null, null);
        System.out.println("res=" + jobRes);
    }

    @Test
    public void insertJob() {
        Job job = new Job();
        job.setCompanyId(1);
        job.setCategoryId(1);
        job.setTitle("Java工程师");
        job.setSalaryLow(25);
        job.setSalaryHigh(40);
        job.setPublisher(1);
        job.setCityId(1);
        JSONObject jobAbstract = new JSONObject();
        jobAbstract.put("education", "本科");
        jobAbstract.put("expirence", "1-5");
        job.setJobAbstract(jobAbstract.toJSONString());
        ResultDto<Integer> res = jobService.insert(job);
        System.out.println("res=" + res);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void search() {
        SearchQuery query = new SearchQuery();
        query.setKeyword("产品");
        query.setLimit(2);
        query.setMaxId(8);
        ResultDto<List<Job>> jobRes = jobService.getJobBySearch(query);
        System.out.printf("res=" + JSON.toJSONString(jobRes));
        Assert.assertTrue(jobRes.isSuccess());
    }
}
