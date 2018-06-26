package com.nankai.wx.job.db.domain;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/5
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class Base {
    private Integer id;
    private Integer ctime;
    private Integer utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public Integer getUtime() {
        return utime;
    }

    public void setUtime(Integer utime) {
        this.utime = utime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Base{");
        sb.append("id=").append(id);
        sb.append(", ctime=").append(ctime);
        sb.append(", utime=").append(utime);
        sb.append('}');
        return sb.toString();
    }
}
