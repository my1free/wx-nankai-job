package com.nankai.wx.job.controller;

import com.alibaba.fastjson.JSONObject;
import com.nankai.wx.job.db.domain.Category;
import com.nankai.wx.job.db.domain.City;
import com.nankai.wx.job.db.service.CategoryService;
import com.nankai.wx.job.db.service.CityService;
import com.nankai.wx.job.dto.NaviDto;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.util.HttpBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Controller
@RequestMapping("/integrated")
public class IntegratedController {
    private static final Logger logger = LoggerFactory.getLogger(IntegratedController.class);

    @Resource
    private CityService cityService;
    @Resource
    private CategoryService categoryService;

    @ResponseBody
    @RequestMapping("/navi")
    public JSONObject navi() {
        logger.error("[navi] [get navi info]");
        try {
            NaviDto naviDto = new NaviDto();

            //city信息
            ResultDto<List<City>> cityRes = cityService.getAllCities();
            if (cityRes.isSuccess()) {
                naviDto.setCities(cityRes.getData());
            }

            //category信息
            ResultDto<List<Category>> categoryRes = categoryService.getAllCategories();
            if (categoryRes.isSuccess()) {
                naviDto.setCategories(categoryRes.getData());
            }

            return HttpBuilder.genData(naviDto);
        } catch (Exception e) {
            logger.error("[navi] [get navi info exception]", e);
            return HttpBuilder.genError(e);
        }
    }
}
