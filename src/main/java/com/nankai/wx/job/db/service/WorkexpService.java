package com.nankai.wx.job.db.service;

import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.WorkexpDao;
import com.nankai.wx.job.db.domain.Workexp;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/24
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class WorkexpService {

    @Resource
    private WorkexpDao workexpDao;

    public ResultDto<List<Workexp>> getWorkexpsByUserId(int userId) {
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        return ResBuilder.genData(workexpDao.getWorkExpByConds(conds));
    }

    public ResultDto<List<Workexp>> getWorkexpsById(int userId, int id) {
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        conds.put("id", id);
        return ResBuilder.genData(workexpDao.getWorkExpByConds(conds));
    }

    public ResultDto<Boolean> insert(int userId, Workexp workexp) {
        ResultDto<Workexp> workexpRes = checkWorkexp(userId, workexp);
        if (!workexpRes.isSuccess()) {
            return ResBuilder.genError(workexpRes.getMsg());
        }
        workexp = workexpRes.getData();
        return ResBuilder.genData(workexpDao.insert(workexp) > 0 ? true : false);
    }

    public ResultDto<Boolean> update(int userId, Workexp workexp) {
        if (userId <= 0) {
            return ResBuilder.genError("invalid user");
        }
        if (workexp == null || workexp.getId() == null) {
            return ResBuilder.genError("invalid id");
        }

        workexp.setUserId(userId);

        return ResBuilder.genData(workexpDao.update(workexp) > 0 ? true : false);
    }

    private ResultDto<Workexp> checkWorkexp(int userId, Workexp workexp) {
        if (workexp == null) {
            return ResBuilder.genError("invalid workexp");
        }
        if (userId <= 0) {
            return ResBuilder.genError("invalid user");
        }
        if (StringUtils.isBlank(workexp.getCompany())) {
            return ResBuilder.genError("公司名称不能为空");
        }
        if (StringUtils.isBlank(workexp.getStartDate())
                || StringUtils.isBlank(workexp.getEndDate())) {
            return ResBuilder.genError("起止时间不能为空");
        }
        if (StringUtils.isBlank(workexp.getPosition())) {
            return ResBuilder.genError("工作职位不能为空");
        }
        if (StringUtils.isBlank(workexp.getContent())) {
            return ResBuilder.genError("工作内容不能为空");
        }
        workexp.setUserId(userId);
        workexp.setValid(1);
        return ResBuilder.genData(workexp);
    }
}
