package com.nankai.wx.job.dto;

import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/24
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class ResumeDto {
    private List<WorkexpDto> workexps;
    private List<EduexpDto> edus;
    private UserDto userDto;

    public List<WorkexpDto> getWorkexps() {
        return workexps;
    }

    public void setWorkexps(List<WorkexpDto> workexps) {
        this.workexps = workexps;
    }

    public List<EduexpDto> getEdus() {
        return edus;
    }

    public void setEdus(List<EduexpDto> edus) {
        this.edus = edus;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResumeDto{");
        sb.append("workexps=").append(workexps);
        sb.append(", edus=").append(edus);
        sb.append(", userDto=").append(userDto);
        sb.append('}');
        return sb.toString();
    }
}
