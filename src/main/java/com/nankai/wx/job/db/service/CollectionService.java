package com.nankai.wx.job.db.service;

import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.CollectionDao;
import com.nankai.wx.job.db.domain.Collection;
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
public class CollectionService {
    @Resource
    private CollectionDao collectionDao;

    public ResultDto<Boolean> insertOrUpdate(Collection collection) {
        if (!checkCollection(collection)) {
            return ResBuilder.genError("参数错误");
        }
        int num = collectionDao.insertOrUpdate(collection);
        if (num > 0) {
            return ResBuilder.genSuccess();
        }
        return ResBuilder.genError("内部错误");
    }

    public ResultDto<Collection> getCollection(Integer userId, Integer jobId) {
        if (NumberUtil.isNullOrZero(userId)
                || NumberUtil.isNullOrZero(jobId)) {
            return ResBuilder.genError("参数错误");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        conds.put("jobId", jobId);
        List<Collection> collections = collectionDao.getCollectionsByConds(conds);
        if (CollectionUtils.isEmpty(collections)) {
            return ResBuilder.genData(null);
        }
        return ResBuilder.genData(collections.get(0));
    }

    public ResultDto<List<Collection>> getCollectionsByPage(int userId, Integer maxId, Integer limit) {
        if (NumberUtil.isNullOrZero(limit)) {
            limit = 20;
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        conds.put("limit", limit);
        conds.put("maxId", maxId);
        conds.put("valid", 1);
        return ResBuilder.genData(collectionDao.getCollectionsByConds(conds));
    }

    public ResultDto<Map<Integer, Collection>> getCollectionMapByPage(int userId, Integer maxId, Integer limit) {
        ResultDto<List<Collection>> collectionRes = getCollectionsByPage(userId, maxId, limit);
        if (!collectionRes.isSuccess() || CollectionUtils.isEmpty(collectionRes.getData())) {
            return ResBuilder.genData(Collections.emptyList());
        }
        Map<Integer, Collection> collectionMap = Maps.newHashMap();
        collectionRes.getData().forEach(collection -> collectionMap.put(collection.getJobId(), collection));
        return ResBuilder.genData(collectionMap);
    }

    private boolean checkCollection(Collection collection) {
        if (collection == null) {
            return false;
        }
        if (NumberUtil.isNullOrZero(collection.getUserId())
                || NumberUtil.isNullOrZero(collection.getJobId())) {
            return false;
        }
        return true;
    }
}
