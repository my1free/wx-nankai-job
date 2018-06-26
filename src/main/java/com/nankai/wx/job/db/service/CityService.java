package com.nankai.wx.job.db.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.CityDao;
import com.nankai.wx.job.db.domain.City;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.CommonUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class CityService {

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    @Resource
    private CityDao cityDao;

    public ResultDto<List<City>> getAllCities() {
        return ResBuilder.genData(cityDao.getAllCities());
    }

    public ResultDto<City> getCityById(Integer cityId) {
        if (cityId == null || cityId <= 0) {
            return ResBuilder.genError("invalid city id");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("id", cityId);
        List<City> cities = cityDao.getCitiesByConds(conds);
        if (CollectionUtils.isEmpty(cities)) {
            return ResBuilder.genData(null);
        }
        return ResBuilder.genData(cities.get(0));
    }

    public ResultDto<Integer> insert(City city) {
        Preconditions.checkArgument(city != null);

        if (StringUtils.isBlank(city.getName())) {
            return ResBuilder.genError("blank name");
        }

        cityDao.insert(city);
        return ResBuilder.genData(city.getId());
    }

    /**
     * 获得cityId和City的映射
     * @param cityIds
     * @return
     */
    public ResultDto<Map<Integer, City>> getCitiesByIds(Set<Integer> cityIds) {
        if (CollectionUtils.isEmpty(cityIds)) {
            return ResBuilder.genError("invalid city ids");
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("ids", cityIds);
        List<City> cities = cityDao.getCitiesByConds(conds);
        return ResBuilder.genData(CommonUtil.convertList2Map(cities));
    }
}
