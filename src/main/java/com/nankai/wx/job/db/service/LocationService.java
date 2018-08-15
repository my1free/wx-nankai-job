package com.nankai.wx.job.db.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nankai.wx.job.common.Constant;
import com.nankai.wx.job.db.dao.CompanyLocationDao;
import com.nankai.wx.job.db.domain.CompanyLocation;
import com.nankai.wx.job.dto.LocationDto;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.NumberUtil;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    @Resource
    private CompanyLocationDao companyLocationDao;

    public ResultDto<List<CompanyLocation>> getCompanyLocations(Integer companyId) {
        if (NumberUtil.isNullOrNonePositive(companyId)) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("companyId", companyId);
        conds.put("limit", 10);
        return ResBuilder.genData(companyLocationDao.getLocationsByConds(conds));
    }

    public ResultDto<List<LocationDto>> getLocationDtos(Integer companyId) {
        if (NumberUtil.isNullOrNonePositive(companyId)) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("companyId", companyId);
        conds.put("limit", 10);
        List<CompanyLocation> locations = companyLocationDao.getLocationsByConds(conds);
        if (CollectionUtils.isEmpty(locations)) {
            return ResBuilder.genData(Collections.emptyList());
        }
        List<LocationDto> locationDtos = locations.stream()
                .map(location -> convertToDto(location))
                .collect(Collectors.toList());
        return ResBuilder.genData(locationDtos);
    }

    public ResultDto<Map<Integer, List<CompanyLocation>>> getCompanyLocationMap(Set<Integer> companyIds) {
        if (CollectionUtils.isEmpty(companyIds)) {
            return ResBuilder.genData(Collections.emptyMap());
        }
        Map<String, Object> conds = Maps.newHashMap();
        conds.put("companyIds", companyIds);
        List<CompanyLocation> locations = companyLocationDao.getLocationsByConds(conds);
        return ResBuilder.genData(convertListToMap(locations));
    }

    public ResultDto<Map<Integer, List<LocationDto>>> getLocationDtoMap(Set<Integer> companyIds) {
        ResultDto<Map<Integer, List<CompanyLocation>>> locationRes = getCompanyLocationMap(companyIds);
        if (!locationRes.isSuccess()) {
            return ResBuilder.genError(locationRes.getMsg());
        }

        Map<Integer, List<LocationDto>> locationDtoMap = Maps.newHashMap();
        locationRes.getData().entrySet().forEach(entry -> {
            int companyId = entry.getKey();
            List<CompanyLocation> locations = entry.getValue();
            locationDtoMap.put(companyId, convertToDto(locations));
        });
        return ResBuilder.genData(locationDtoMap);
    }

    /**
     * 单个插入Company Location
     *
     * @param location
     * @return
     */
    public ResultDto<CompanyLocation> insertCompanyLocation(CompanyLocation location) {
        ResultDto checkRes = checkCompanyLocation(location);
        if (!checkRes.isSuccess()) {
            return checkRes;
        }
        companyLocationDao.insert(location);
        return ResBuilder.genData(location);

    }

    /**
     * 批量插入Company location
     *
     * @param locations
     * @return
     */
    public ResultDto<List<ResultDto<CompanyLocation>>> insertCompanyLocations(List<CompanyLocation> locations) {
        try {
            if (CollectionUtils.isEmpty(locations)) {
                return ResBuilder.genError("empty list");
            }
            List<ResultDto<CompanyLocation>> res = Lists.newArrayList();
            locations.forEach(location -> {
                res.add(insertCompanyLocation(location));
            });
            return ResBuilder.genData(res);
        } catch (Exception e) {
            logger.error("db exception", e);
            return ResBuilder.genError(Constant.DB_ERROR);
        }
    }

    public ResultDto<CompanyLocation> updateCompanyLocation(CompanyLocation location) {
        if (location == null
                || NumberUtil.isNullOrNonePositive(location.getId())) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        int num = companyLocationDao.update(location);
        if (num <= 0) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        return ResBuilder.genData(location);
    }

    public ResultDto<List<ResultDto<CompanyLocation>>> updateCompanyLocations(List<CompanyLocation> locations) {
        if (CollectionUtils.isEmpty(locations)) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        List<ResultDto<CompanyLocation>> res = Lists.newArrayList();
        locations.forEach(location -> {
            res.add(updateCompanyLocation(location));
        });
        return ResBuilder.genData(res);
    }

    public ResultDto<Integer> removeCompanyLocation(CompanyLocation location) {
        if (location == null || NumberUtil.isNullOrNonePositive(location.getId())) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        return ResBuilder.genData(companyLocationDao.remove(location));
    }

    public ResultDto<List<ResultDto<Integer>>> removeCompanyLocations(List<CompanyLocation> locations) {
        if (CollectionUtils.isEmpty(locations)) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        List<ResultDto<Integer>> removeRes = Lists.newArrayList();
        locations.forEach(location -> {
            removeRes.add(removeCompanyLocation(location));
        });
        return ResBuilder.genData(removeRes);
    }

    private ResultDto<CompanyLocation> checkCompanyLocation(CompanyLocation location) {
        if (location == null) {
            return ResBuilder.genError(Constant.INVALID_PARAM);
        }
        if (NumberUtil.isNullOrNonePositive(location.getCompanyId())) {
            return ResBuilder.genError("company id should be positive");
        }
        if (StringUtils.isBlank(location.getTitle())) {
            return ResBuilder.genError("title should not be blank");
        }
        if (StringUtils.isBlank(location.getAddress())) {
            return ResBuilder.genError("address should not be blank");
        }
        if (StringUtils.isBlank(location.getLat())) {
            return ResBuilder.genError("lat should not be blank");
        }
        if (StringUtils.isBlank(location.getLng())) {
            return ResBuilder.genError("lng should not be blank");
        }
        return ResBuilder.genData(null);
    }

    private Map<Integer, List<CompanyLocation>> convertListToMap(List<CompanyLocation> locations) {
        if (CollectionUtils.isEmpty(locations)) {
            return Collections.emptyMap();
        }
        Map<Integer, List<CompanyLocation>> locationsMap = Maps.newHashMap();
        locations.forEach(location -> {
            List<CompanyLocation> tmpLocations = locationsMap.get(location.getCompanyId());
            if (tmpLocations != null) {
                tmpLocations.add(location);
            }else {
                tmpLocations = Lists.newArrayList();
                tmpLocations.add(location);
                locationsMap.put(location.getCompanyId(), tmpLocations);
            }
        });
        return locationsMap;
    }

    private LocationDto convertToDto(CompanyLocation location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setTitle(location.getTitle());
        locationDto.setAddress(location.getAddress());
        locationDto.setLng(location.getLng());
        locationDto.setLat(location.getLng());
        return locationDto;
    }

    private List<LocationDto> convertToDto(List<CompanyLocation> locations) {
        if (CollectionUtils.isEmpty(locations)) {
            return Collections.emptyList();
        }
        return locations.stream()
                .map(location -> convertToDto(location))
                .collect(Collectors.toList());
    }
}
