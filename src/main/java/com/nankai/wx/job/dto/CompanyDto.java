package com.nankai.wx.job.dto;

import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/6
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class CompanyDto {
    private Integer id;
    private String name;
    private String fullname;
    private String logo;
    private String introduction;
    private List<LocationDto> locations;
    private List<JobInfoDto> jobInfoDtos;
    private boolean concerned;
    private boolean belonged;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
    }

    public List<JobInfoDto> getJobInfoDtos() {
        return jobInfoDtos;
    }

    public void setJobInfoDtos(List<JobInfoDto> jobInfoDtos) {
        this.jobInfoDtos = jobInfoDtos;
    }

    public boolean isConcerned() {
        return concerned;
    }

    public void setConcerned(boolean concerned) {
        this.concerned = concerned;
    }

    public boolean isBelonged() {
        return belonged;
    }

    public void setBelonged(boolean belonged) {
        this.belonged = belonged;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CompanyDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", fullname='").append(fullname).append('\'');
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", locations=").append(locations);
        sb.append(", jobInfoDtos=").append(jobInfoDtos);
        sb.append(", concerned=").append(concerned);
        sb.append(", belonged=").append(belonged);
        sb.append('}');
        return sb.toString();
    }
}
