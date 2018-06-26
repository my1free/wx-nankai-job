package com.nankai.wx.job.db.service;

import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.ConcernedDao;
import com.nankai.wx.job.db.domain.Concerned;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.NumberUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/15
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class ConcernedService {
    @Resource
    private ConcernedDao concernedDao;

    public ResultDto<Boolean> insertOrUpdate(Concerned concerned) {
        if (!checkDomain(concerned)) {
            return ResBuilder.genError("参数错误");
        }
        int num = concernedDao.insertOrUpdate(concerned);
        if (num > 0) {
            return ResBuilder.genSuccess();
        }
        return ResBuilder.genError("内部错误");
    }

    public ResultDto<Concerned> getConcerned(Integer userId, Integer companyId) {
        if (NumberUtil.isNullOrZero(userId)
                || NumberUtil.isNullOrZero(companyId)) {
            return ResBuilder.genError("参数错误");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        conds.put("companyId", companyId);
        List<Concerned> concernedList = concernedDao.getConcernedByConds(conds);
        if (CollectionUtils.isEmpty(concernedList)) {
            return ResBuilder.genData(null);
        }
        return ResBuilder.genData(concernedList.get(0));
    }

    public ResultDto<List<Concerned>> getConcernedByPage(int userId, Integer maxId, Integer limit) {
        if (NumberUtil.isNullOrZero(limit)) {
            limit = 20;
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        conds.put("limit", limit);
        conds.put("maxId", maxId);
        conds.put("valid", 1);
        return ResBuilder.genData(concernedDao.getConcernedByConds(conds));
    }

    public ResultDto<Map<Integer, Concerned>> getConcernedMapByPage(int userId, Integer maxId, Integer limit) {
        ResultDto<List<Concerned>> concernedRes = getConcernedByPage(userId, maxId, limit);
        if (!concernedRes.isSuccess() || CollectionUtils.isEmpty(concernedRes.getData())) {
            return ResBuilder.genData(Collections.emptyList());
        }
        Map<Integer, Concerned> concernedMap = Maps.newHashMap();
        concernedRes.getData().forEach(concerned -> concernedMap.put(concerned.getCompanyId(), concerned));
        return ResBuilder.genData(concernedMap);
    }

    private boolean checkDomain(Concerned concerned) {
        if (concerned == null) {
            return false;
        }
        if (NumberUtil.isNullOrZero(concerned.getUserId())
                || NumberUtil.isNullOrZero(concerned.getCompanyId())) {
            return false;
        }
        return true;
    }
}
