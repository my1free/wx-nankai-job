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
    private String logo;
    private String compayAbstract;
    private String description;
    private List<JobInfoDto> jobInfoDtos;
    private boolean concerned;

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

    public String getCompayAbstract() {
        return compayAbstract;
    }

    public void setCompayAbstract(String compayAbstract) {
        this.compayAbstract = compayAbstract;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CompanyDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", compayAbstract='").append(compayAbstract).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", jobInfoDtos=").append(jobInfoDtos);
        sb.append(", concerned=").append(concerned);
        sb.append('}');
        return sb.toString();
    }
}
