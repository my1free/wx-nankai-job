package com.nankai.wx.job.service;

import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.db.domain.CompanyLocation;
import com.nankai.wx.job.db.domain.CompanyModifier;
import com.nankai.wx.job.db.domain.Concerned;
import com.nankai.wx.job.db.domain.User;
import com.nankai.wx.job.db.service.BelongedService;
import com.nankai.wx.job.db.service.CompanyService;
import com.nankai.wx.job.db.service.ConcernedService;
import com.nankai.wx.job.db.service.LocationService;
import com.nankai.wx.job.db.service.ModifierService;
import com.nankai.wx.job.db.service.UserService;
import com.nankai.wx.job.dto.CompanyDto;
import com.nankai.wx.job.dto.JobInfoDto;
import com.nankai.wx.job.dto.JobQuery;
import com.nankai.wx.job.dto.LocationDto;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.ResBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class CompanyInfoService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyInfoService.class);

    @Resource
    private CompanyService companyService;
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private UserService userService;
    @Resource
    private ConcernedService concernedService;
    @Resource
    private LocationService locationService;
    @Resource
    private ModifierService modifierService;
    @Resource
    private BelongedService belongedService;

    public ResultDto<CompanyDto>getCompanyDetail(String openid, Integer companyId) {
        ResultDto<Company> companyRes = companyService.getCompanyById(companyId);
        if (!companyRes.isSuccess() || companyRes.getData() == null) {
            return ResBuilder.genSuccess("no company");
        }

        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }

        CompanyDto companyDto = fillCompany(companyRes.getData(), userRes.getData().getId());

        JobQuery query = new JobQuery();
        query.setCompanyId(companyId);

        ResultDto<List<JobInfoDto>> jobRes = jobInfoService.getJobListByQuery(query);
        if (!jobRes.isSuccess()) {
            return ResBuilder.genError(jobRes.getMsg());
        }
        companyDto.setJobInfoDtos(jobRes.getData());
        return ResBuilder.genData(companyDto);
    }

    public ResultDto<CompanyDto> getCompanyInfo(Integer companyId){
        ResultDto<Company> companyRes = companyService.getCompanyById(companyId);
        if (!companyRes.isSuccess() || companyRes.getData() == null) {
            return ResBuilder.genSuccess("no company");
        }

        //get locations
        ResultDto<List<LocationDto>> locationsRes
                = locationService.getLocationDtos(companyId);
        if (!locationsRes.isSuccess()) {
            return ResBuilder.genError(locationsRes.getMsg());
        }

        CompanyDto companyDto = fillCompany(companyRes.getData());
        companyDto.setLocations(locationsRes.getData());
        return ResBuilder.genData(companyDto);
    }

    //TODO 这里可以并行
    public ResultDto<List<CompanyDto>> getCompanyByIds(Set<Integer> companyIds) {
        if (CollectionUtils.isEmpty(companyIds)) {
            return ResBuilder.genData(Collections.emptyList());
        }
        ResultDto<Map<Integer, Company>> companyRes = companyService.getCompanyMapByIds(companyIds);
        if (!companyRes.isSuccess()) {
            return ResBuilder.genError(companyRes.getMsg());
        }
        if (MapUtils.isEmpty(companyRes.getData())) {
            return ResBuilder.genData(Collections.emptyList());
        }

        //get locations
        ResultDto<Map<Integer, List<LocationDto>>> locationDtoMapRes
                = locationService.getLocationDtoMap(companyIds);
        if (!locationDtoMapRes.isSuccess()) {
            return ResBuilder.genError(locationDtoMapRes.getMsg());
        }
        List<CompanyDto> companyDtos = companyRes.getData().values()
                .stream()
                .map(company -> fillCompany(company))
                .collect(Collectors.toList());
        companyDtos.forEach(companyDto -> {
            companyDto.setLocations(locationDtoMapRes.getData().get(companyDto.getId()));
        });
        return ResBuilder.genData(companyDtos);
    }

    public ResultDto<List<CompanyDto>> searchCompany(String openid,
                                                     String keyword,
                                                     Integer maxId,
                                                     Integer limit) {
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        ResultDto<List<Company>> companyRes = companyService.getCompanyByKeyword(keyword, maxId, limit);
        if (!companyRes.isSuccess()) {
            return ResBuilder.genError(companyRes.getMsg());
        }
        if (CollectionUtils.isEmpty(companyRes.getData())) {
            return ResBuilder.genData(Collections.emptyList());
        }

        List<CompanyDto> companyDtos = companyRes.getData()
                .stream()
                .map(company -> fillCompany(company))
                .collect(Collectors.toList());

        //get locations
        Set<Integer> companyIds = companyRes.getData().stream()
                .map(Company::getId)
                .collect(Collectors.toSet());
        ResultDto<Map<Integer, List<LocationDto>>> locationDtoMapRes
                = locationService.getLocationDtoMap(companyIds);
        if (!locationDtoMapRes.isSuccess()) {
            locationDtoMapRes.setData(Collections.emptyMap());
            logger.error("[searchCompany] [get locations error] companyIds={}", companyIds);
//            return ResBuilder.genError(locationDtoMapRes.getMsg());
        }

        //get belonged relation
        ResultDto<Map<Integer, Boolean>> belongedMapRes
                = belongedService.getBelongedMap(userRes.getData().getId(), companyIds);
        if (!belongedMapRes.isSuccess()) {
            belongedMapRes.setData(Collections.emptyMap());
            logger.error("[searchCompany] [get belonged error] companyIds={}", companyIds);
//            return ResBuilder.genError(belongedMapRes.getMsg());
        }

        companyDtos.forEach(companyDto -> {
            companyDto.setLocations(locationDtoMapRes.getData().get(companyDto.getId()));
            companyDto.setBelonged(belongedMapRes.getData().get(companyDto.getId()));
        });


        return ResBuilder.genData(companyDtos);
    }

    public ResultDto<Boolean> insertCompany(String openid,
                                            Company company,
                                            List<CompanyLocation> locations) {
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }

        ResultDto<Company> companyRes = companyService.insert(company);
        if (!companyRes.isSuccess()) {
            return ResBuilder.genError(companyRes.getMsg());
        }

        //addLocations
        locations.forEach(location -> {
            location.setCompanyId(companyRes.getData().getId());
        });

        //insert locations
        locationService.insertCompanyLocations(locations);

        //insert modifier
        modifierService.insertCompanyModifier(
                new CompanyModifier(companyRes.getData().getId(), userRes.getData().getId()));

        return ResBuilder.genData(true);
    }

    public ResultDto<Boolean> modifyCompany(String openid,
                                            Company company,
                                            List<CompanyLocation> addedLocations,
                                            List<CompanyLocation> modifiedLocations,
                                            List<CompanyLocation> removedLocations) {
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }

        //update company
        ResultDto<Integer> updateRes = companyService.update(company);
        if (!updateRes.isSuccess()) {
            return ResBuilder.genError(updateRes.getMsg());
        }

        //update locations
        if (CollectionUtils.isNotEmpty(addedLocations)) {
            ResultDto res = locationService.insertCompanyLocations(addedLocations);
            logger.info("[modifyCompany] addedLocations={}, res={}", addedLocations, res);
        }
        if (CollectionUtils.isNotEmpty(modifiedLocations)) {
            ResultDto res = locationService.updateCompanyLocations(modifiedLocations);
            logger.info("[modifyCompany] modifiedLocations={}, res={}", modifiedLocations, res);
        }
        if (CollectionUtils.isNotEmpty(removedLocations)) {
            ResultDto res = locationService.removeCompanyLocations(removedLocations);
            logger.info("[modifyCompany] removeLocations={}, res={}", removedLocations, res);
        }

        //insert modifier
        modifierService.insertCompanyModifier(
                new CompanyModifier(company.getId(), userRes.getData().getId()));

        return ResBuilder.genData(true);
    }

    private CompanyDto fillCompany(Company company) {
        CompanyDto companyDto = new CompanyDto();
        BeanUtils.copyProperties(company, companyDto);
        return companyDto;
    }

    public CompanyDto fillCompany(Company company, int userId) {
        CompanyDto companyDto = new CompanyDto();
        BeanUtils.copyProperties(company, companyDto);

        ResultDto<Concerned> concernedRes = concernedService.getConcerned(userId, company.getId());
        if (concernedRes.isSuccess() && concernedRes.getData() != null) {
            companyDto.setConcerned(concernedRes.getData().getValid() == 1);
        }
        return companyDto;
    }
}
