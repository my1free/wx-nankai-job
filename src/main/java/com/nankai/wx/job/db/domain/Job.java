package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class Job extends Base {
    //required
    private Integer companyId;
    private Integer categoryId;
    //required
    private String title;
    private String jobAbstract;
    //required
    private Integer salaryLow;
    //required
    private Integer salaryHigh;
    //required
    private Integer cityId;
    private Integer tagId;
    private String description;
    private String location;
    private Integer publisher;
    private Short status;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobAbstract() {
        return jobAbstract;
    }

    public void setJobAbstract(String jobAbstract) {
        this.jobAbstract = jobAbstract;
    }

    public Integer getSalaryLow() {
        return salaryLow;
    }

    public void setSalaryLow(Integer salaryLow) {
        this.salaryLow = salaryLow;
    }

    public Integer getSalaryHigh() {
        return salaryHigh;
    }

    public void setSalaryHigh(Integer salaryHigh) {
        this.salaryHigh = salaryHigh;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Job{");
        sb.append("companyId=").append(companyId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", jobAbstract='").append(jobAbstract).append('\'');
        sb.append(", salaryLow=").append(salaryLow);
        sb.append(", salaryHigh=").append(salaryHigh);
        sb.append(", cityId=").append(cityId);
        sb.append(", tagId=").append(tagId);
        sb.append(", description='").append(description).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", publisher=").append(publisher);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
