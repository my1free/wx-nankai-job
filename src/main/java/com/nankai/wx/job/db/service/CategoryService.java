package com.nankai.wx.job.db.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.nankai.wx.job.db.dao.CategoryDao;
import com.nankai.wx.job.db.domain.Category;
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
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryDao categoryDao;

    public ResultDto<List<Category>> getAllCategories() {
        return ResBuilder.genData(categoryDao.getAllCategories());
    }


    public ResultDto<Integer> insert(Category category) {
        Preconditions.checkArgument(category != null);

        if (StringUtils.isBlank(category.getName())) {
            return ResBuilder.genError("blank name");
        }

        categoryDao.insert(category);
        return ResBuilder.genData(category.getId());
    }
}
