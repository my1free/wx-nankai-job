package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.CompanyLocation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface CompanyLocationDao {

    @Select("SELECT * FROM company_location WHERE company_id=#{companyId} AND valid=1")
    List<CompanyLocation> getLocations(Integer companyId);

    @SelectProvider(type = SqlProvider.class, method = "select")
    List<CompanyLocation> getLocationsByConds(Map<String, Object> conds);

    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(CompanyLocation location);

    @UpdateProvider(type = SqlProvider.class, method = "update")
    int update(CompanyLocation location);

    @Update("UPDATE company_location SET valid=0 WHERE id=#{id}")
    int remove(CompanyLocation location);

    class SqlProvider{
        public String insert(CompanyLocation location) {
            BEGIN();
            INSERT_INTO("company_location");
            VALUES("company_id", "#{companyId}");
            VALUES("title", "#{title}");
            if(StringUtils.isNotBlank(location.getAddress())) {
                VALUES("address", "#{address}");
            }
            VALUES("lat", "#{lat}");
            VALUES("lng", "#{lng}");
            VALUES("valid", "1");
            VALUES("ctime", "unix_timestamp()");
            VALUES("utime", "unix_timestamp()");
            return SQL();
        }

        public String update(CompanyLocation location) {
            BEGIN();
            UPDATE("company_location");
            SET("utime=unix_timestamp()");

            if (StringUtils.isNotBlank(location.getTitle())) {
                SET("title=#{title}");
            }
            if (StringUtils.isNotBlank(location.getAddress())) {
                SET("address=#{address}");
            }
            if (StringUtils.isNotBlank(location.getLat())) {
                SET("lat=#{lat}");
            }
            if (StringUtils.isNotBlank(location.getLng())) {
                SET("lng=#{lng}");
            }
            WHERE("id=#{id}");
            return SQL();
        }

        public String select(Map<String, Object> conds) {
            BEGIN();
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("company_location");
            if (conds.get("id") != null) {
                WHERE("id=#{id}");
            }
            if (conds.get("company_id") != null) {
                WHERE("company_id=#{companyId}");
            }
            if (CollectionUtils.isNotEmpty(((Set) conds.get("companyIds")))) {
                Set<Integer> companyIds = (Set<Integer>) conds.get("companyIds");
                WHERE("company_id in (" + StringUtils.join(companyIds, ",") + ")");
            }
            WHERE("valid=1");
            ORDER_BY("id DESC");
            String sql = SQL();
            if (conds.get("limit") != null) {
                sql += " limit #{limit}";
            }
            return sql;
        }
    }
}
