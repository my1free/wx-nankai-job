package com.nankai.wx.job.test.db;

import com.nankai.wx.job.db.domain.Category;
import com.nankai.wx.job.db.service.CategoryService;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.test.TestBase;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CategoryServiceTest extends TestBase {
    @Resource
    private CategoryService categoryService;

    @Test
    public void insert() {
        Category category = new Category();
        category.setName("技术");
        ResultDto<Integer> res = categoryService.insert(category);

        category.setName("产品");
        res = categoryService.insert(category);

        category.setName("测试");
        res = categoryService.insert(category);

        category.setName("行政");
        res = categoryService.insert(category);

        category.setName("设计");
        res = categoryService.insert(category);

        category.setName("运营");
        res = categoryService.insert(category);
        System.out.println("res=" + res);
        Assert.assertTrue(res.isSuccess());
    }
}
