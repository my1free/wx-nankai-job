package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.Eduexp;
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
public interface EduexpDao {

    @Options(useGeneratedKeys = true)
    @Insert("insert into eduexp (user_id, name, start_date, end_date, major, degree, " +
            "valid, ctime, utime) values(#{userId}, #{name}, #{startDate}, #{endDate}, #{major}, " +
            "#{degree}, 1, unix_timestamp(), unix_timestamp())")
    int insert(Eduexp eduexp);

    @UpdateProvider(type = SqlProvider.class, method = "update")
    int update(Eduexp eduexp);

    @SelectProvider(type = SqlProvider.class, method = "getEduexpByConds")
    List<Eduexp> getEduexpByConds(Map<String, Object> conds);

    class SqlProvider {
        public String getEduexpByConds(Map<String, Object> conds) {
            BEGIN();
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("eduexp");
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

        public String update(Eduexp eduexp) {
            BEGIN();
            UPDATE("eduexp");
            if (StringUtils.isNotBlank(eduexp.getName())) {
                SET("name=#{name}");
            }
            if (StringUtils.isNotBlank(eduexp.getStartDate())) {
                SET("start_date=#{startDate}");
            }
            if (StringUtils.isNotBlank(eduexp.getEndDate())) {
                SET("end_date=#{endDate}");
            }
            if (StringUtils.isNotBlank(eduexp.getMajor())) {
                SET("major=#{major}");
            }
            if (StringUtils.isNotBlank(eduexp.getDegree())) {
                SET("degree=#{degree}");
            }
            SET("utime=unix_timestamp()");
            WHERE("id=#{id}");
            WHERE("user_id=#{userId}");
            return SQL();
        }
    }
}
