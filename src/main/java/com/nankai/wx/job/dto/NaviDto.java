package com.nankai.wx.job.dto;

import com.nankai.wx.job.db.domain.Category;
import com.nankai.wx.job.db.domain.City;

import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/6
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class NaviDto {
    private List<City> cities;
    private List<Category> categories;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NaviDto{");
        sb.append("cities=").append(cities);
        sb.append(", categories=").append(categories);
        sb.append('}');
        return sb.toString();
    }
}
