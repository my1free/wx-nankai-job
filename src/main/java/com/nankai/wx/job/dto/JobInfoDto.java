package com.nankai.wx.job.dto;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class JobInfoDto {
    //job信息
    private Integer id;
    private String title;
    private Integer cityId;
    private Integer categoryId;
    private String cityName;
    private String jobAbstract;
    private Integer salaryLow;
    private Integer salaryHigh;
    private Short status;

    //学历
    private String education;
    //工作经验
    private String expirence;

    //company信息
    private Integer companyId;
    private String companyName;
    private String companyLogo;

    //tag信息
    private Integer tagId;
    private String tagName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExpirence() {
        return expirence;
    }

    public void setExpirence(String expirence) {
        this.expirence = expirence;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobInfoDto{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", cityId=").append(cityId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", cityName='").append(cityName).append('\'');
        sb.append(", jobAbstract='").append(jobAbstract).append('\'');
        sb.append(", salaryLow=").append(salaryLow);
        sb.append(", salaryHigh=").append(salaryHigh);
        sb.append(", status=").append(status);
        sb.append(", education='").append(education).append('\'');
        sb.append(", expirence='").append(expirence).append('\'');
        sb.append(", companyId=").append(companyId);
        sb.append(", companyName='").append(companyName).append('\'');
        sb.append(", companyLogo='").append(companyLogo).append('\'');
        sb.append(", tagId=").append(tagId);
        sb.append(", tagName='").append(tagName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
