package com.nankai.wx.job.db.service;

import com.google.common.collect.Maps;
import com.nankai.wx.job.common.Constant;
import com.nankai.wx.job.db.dao.CompanyBelongedDao;
import com.nankai.wx.job.db.domain.CompanyBelonged;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.NumberUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class BelongedService {
    private static final Logger logger = LoggerFactory.getLogger(BelongedService.class);

    @Resource
    private CompanyBelongedDao companyBelongedDao;


    public ResultDto<List<CompanyBelonged>> getBelongedCompanies(Integer userId) {
        if (NumberUtil.isNullOrNonePositive(userId)) {
            return ResBuilder.genError(Constant.INVALID_USER);
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        return ResBuilder.genData(companyBelongedDao.getBelongedCompanies(conds));
    }

    public ResultDto<List<CompanyBelonged>> getBelongedCompanies(Integer userId, Integer companyId) {
        if (NumberUtil.isNullOrNonePositive(userId)) {
            return ResBuilder.genError(Constant.INVALID_USER);
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        conds.put("companyId", companyId);
        return ResBuilder.genData(companyBelongedDao.getBelongedCompanies(conds));
    }

    /**
     * insert
     * <p>需要保证user和company的唯一性</p>
     * @param belonged
     * @return
     */
    public ResultDto<CompanyBelonged> insert(CompanyBelonged belonged) {
        ResultDto<CompanyBelonged> checkRes = checkBelonged(belonged);
        if (!checkRes.isSuccess()) {
            return checkRes;
        }
        //检测是否已经存在
        CompanyBelonged existBelonged = companyBelongedDao.checkExist(belonged.getUserId(), belonged.getCompanyId());
        //已经存在记录
        if (existBelonged != null) {
            if (existBelonged.getValid() == 1) {
                return ResBuilder.genData(existBelonged);
            }else {
                companyBelongedDao.setValid(existBelonged.getId());
                existBelonged.setValid(1);
                return ResBuilder.genData(existBelonged);
            }
        }
        //不存在记录
        if (companyBelongedDao.insert(belonged) == 1) {
            return ResBuilder.genData(belonged);
        }
        return ResBuilder.genError(Constant.INVALID_PARAM);
    }

    public ResultDto<Boolean> remove(Integer id) {
        if (NumberUtil.isNullOrNonePositive(id)) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        if (companyBelongedDao.setInValid(id) == 1) {
            return ResBuilder.genData(true);
        }
        return ResBuilder.genError(Constant.INVALID_PARAM);
    }

    public ResultDto<Map<Integer, Boolean>> getBelongedMap(Integer userId, Set<Integer> companyIds) {
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("userId", userId);
        conds.put("companyIds", companyIds);
        List<CompanyBelonged> belongeds = companyBelongedDao.getBelongedCompanies(conds);
        Map<Integer, CompanyBelonged> belongedMap = belongeds.stream()
                .collect(Collectors.toMap(
                        CompanyBelonged::getCompanyId,
                        Function.<CompanyBelonged>identity()));
        Map<Integer, Boolean> res = companyIds.stream()
                .collect(Collectors.toMap(id -> id, id -> belongedMap.get(id) != null));
        return ResBuilder.genData(res);
    }

    private ResultDto<CompanyBelonged> checkBelonged(CompanyBelonged belonged) {
        if (belonged == null) {
            return ResBuilder.genError("invalid parameters");
        }
        if (NumberUtil.isNullOrNonePositive(belonged.getCompanyId())) {
            return ResBuilder.genError("invalid company id");
        }
        if (NumberUtil.isNullOrNonePositive(belonged.getUserId())) {
            return ResBuilder.genError("invalid user id");
        }
        return ResBuilder.genData(null);
    }
}
