package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.City;
import com.nankai.wx.job.db.domain.Company;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface CityDao {
    @Select("select * from city")
    List<City> getAllCities();

    @SelectProvider(type = SqlProvider.class, method = "getCitiesByConds")
    List<City> getCitiesByConds(Map<String, Object> conds);

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(City city);

    class SqlProvider {
        public String insert(City city) {
            BEGIN();
            INSERT_INTO("city");
            VALUES("name", "#{name}");
            VALUES("ctime", "unix_timestamp()");
            VALUES("utime", "unix_timestamp()");

            return SQL();
        }

        public String getCitiesByConds(Map<String, Object> conds) {
            BEGIN();
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("city");

            if (conds.get("id") != null) {
                WHERE("id=#{id}");
            }

            if (CollectionUtils.isNotEmpty((Set<Integer>) conds.get("ids"))) {
                Set<Integer> ids = (Set<Integer>) conds.get("ids");
                WHERE("id in (" + StringUtils.join(ids, ",") + ")");
            }

            return SQL();
        }
    }
}
