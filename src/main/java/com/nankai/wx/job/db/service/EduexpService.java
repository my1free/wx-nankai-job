package com.nankai.wx.job.db.service;

import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.EduexpDao;
import com.nankai.wx.job.db.domain.Eduexp;
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
public class EduexpService {

    @Resource
    private EduexpDao eduexpDao;

    public ResultDto<List<Eduexp>> getEduexpsByUserId(int userId) {
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        return ResBuilder.genData(eduexpDao.getEduexpByConds(conds));
    }

    public ResultDto<List<Eduexp>> getEduexpsById(int userId, int id) {
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        conds.put("id", id);
        return ResBuilder.genData(eduexpDao.getEduexpByConds(conds));
    }

    public ResultDto<Boolean> insert(int userId, Eduexp eduexp) {
        ResultDto<Eduexp> eduexpRes = checkEduexp(userId, eduexp);
        if (!eduexpRes.isSuccess()) {
            return ResBuilder.genError(eduexpRes.getMsg());
        }
        eduexp = eduexpRes.getData();
        return ResBuilder.genData(eduexpDao.insert(eduexp) > 0 ? true : false);
    }

    public ResultDto<Boolean> update(int userId, Eduexp eduexp) {
        if (userId <= 0) {
            return ResBuilder.genError("invalid user");
        }
        if (eduexp == null || eduexp.getId() == null) {
            return ResBuilder.genError("invalid id");
        }

        eduexp.setUserId(userId);

        return ResBuilder.genData(eduexpDao.update(eduexp) > 0 ? true : false);
    }

    private ResultDto<Eduexp> checkEduexp(int userId, Eduexp eduexp) {
        if (eduexp == null) {
            return ResBuilder.genError("invalid Eduexp");
        }
        if (userId <= 0) {
            return ResBuilder.genError("invalid user");
        }
        if (StringUtils.isBlank(eduexp.getName())) {
            return ResBuilder.genError("学校名称不能为空");
        }
        if (StringUtils.isBlank(eduexp.getStartDate())
                || StringUtils.isBlank(eduexp.getEndDate())) {
            return ResBuilder.genError("起止时间不能为空");
        }
        if (StringUtils.isBlank(eduexp.getMajor())) {
            return ResBuilder.genError("专业不能为空");
        }
        if (StringUtils.isBlank(eduexp.getDegree())) {
            return ResBuilder.genError("学历不能为空");
        }
        eduexp.setUserId(userId);
        eduexp.setValid(1);
        return ResBuilder.genData(eduexp);
    }
}
