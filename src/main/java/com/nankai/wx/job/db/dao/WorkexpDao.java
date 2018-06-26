package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.Workexp;
import com.nankai.wx.job.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/3
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface WorkexpDao {

    @Options(useGeneratedKeys = true)
    @Insert("insert into workexp (user_id, company, start_date, end_date, position, content, " +
            "valid, ctime, utime) values(#{userId}, #{company}, #{startDate}, #{endDate}, #{position}, " +
            "#{content}, 1, unix_timestamp(), unix_timestamp())")
    int insert(Workexp workexp);

    @UpdateProvider(type = SqlProvider.class, method = "update")
    int update(Workexp workexp);

    @SelectProvider(type = SqlProvider.class, method = "getWorkExpByConds")
    List<Workexp> getWorkExpByConds(Map<String, Object> conds);

    class SqlProvider {
        public String getWorkExpByConds(Map<String, Object> conds) {
            BEGIN();
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("workexp");
            if (!NumberUtil.isNullOrZero((Integer) conds.get("id"))) {
                WHERE("id=#{id}");
            }
            if (!NumberUtil.isNullOrZero((Integer) conds.get("userId"))) {
                WHERE("user_id=#{userId}");
            }
            WHERE("valid=1");
            ORDER_BY("id desc");
            return SQL();
        }

        public String update(Workexp workexp) {
            BEGIN();
            UPDATE("workexp");
            if (StringUtils.isNotBlank(workexp.getCompany())) {
                SET("company=#{company}");
            }
            if (StringUtils.isNotBlank(workexp.getStartDate())) {
                SET("start_date=#{startDate}");
            }
            if (StringUtils.isNotBlank(workexp.getEndDate())) {
                SET("end_date=#{endDate}");
            }
            if (StringUtils.isNotBlank(workexp.getPosition())) {
                SET("position=#{position}");
            }
            if (StringUtils.isNotBlank(workexp.getContent())) {
                SET("content=#{content}");
            }
            SET("utime=unix_timestamp()");
            WHERE("id=#{id}");
            WHERE("user_id=#{userId}");
            return SQL();
        }
    }
}
