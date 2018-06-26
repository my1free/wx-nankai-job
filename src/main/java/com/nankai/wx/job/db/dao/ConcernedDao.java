package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.Concerned;
import com.nankai.wx.job.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/15
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface ConcernedDao {
    @Insert("insert into concerned (user_id, company_id, valid, ctime, utime) " +
            "values(#{userId}, #{companyId}, #{valid}, unix_timestamp(), unix_timestamp()) " +
            "on duplicate key update valid=#{valid}, utime=unix_timestamp()")
    int insertOrUpdate(Concerned concerned);

    @SelectProvider(type = SqlProvider.class, method = "getConcernedByConds")
    List<Concerned> getConcernedByConds(Map<String, Object> conds);

    class SqlProvider {
        public String getConcernedByConds(Map<String, Object> conds) {
            BEGIN();
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("concerned");

            if (conds.get("userId") != null && (Integer) conds.get("userId") != 0) {
                WHERE("user_id=#{userId}");
            }

            if (conds.get("companyId") != null && (Integer) conds.get("companyId") != 0) {
                WHERE("company_id=#{companyId}");
            }

            if (!NumberUtil.isNullOrZero((Integer) conds.get("maxId"))) {
                WHERE("id<#{maxId}");
            }

            if (conds.get("valid") != null) {
                WHERE("valid=#{valid}");
            }

            ORDER_BY("utime desc");

            String sql = SQL();

            if (!NumberUtil.isNullOrZero((Integer)conds.get("limit"))) {
                sql += " limit #{limit}";
            }

            return sql;
        }
    }
}
