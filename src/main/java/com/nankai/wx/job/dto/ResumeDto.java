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
}
