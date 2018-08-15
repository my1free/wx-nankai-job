package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.Company;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.InsertProvider;
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
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface CompanyDao {

    @SelectProvider(type = SqlProvider.class, method = "getCompanyByConds")
    List<Company> getCompanyByConds(Map<String, Object> conds);

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(Company company);

    @UpdateProvider(type = SqlProvider.class, method = "update")
    int update(Company company);


    class SqlProvider {

        public String insert(Company company) {
            BEGIN();
            INSERT_INTO("company");
            VALUES("name", "#{name}");
            VALUES("ctime", "unix_timestamp()");
            VALUES("utime", "unix_timestamp()");

            if(StringUtils.isNotBlank(company.getLogo())) {
                VALUES("logo", "#{logo}");
            }
            if(StringUtils.isNotBlank(company.getFullname())) {
                VALUES("fullname", "#{fullname}");
            }
            if(StringUtils.isNotBlank(company.getIntroduction())) {
                VALUES("introduction", "#{introduction}");
            }
            return SQL();
        }

        public String update(Company company) {
            BEGIN();
            UPDATE("company");
            if (StringUtils.isNotBlank(company.getName())) {
                SET("name=#{name}");
            }
            if (StringUtils.isNotBlank(company.getFullname())) {
                SET("fullname=#{fullname}");
            }
            if (StringUtils.isNotBlank(company.getLogo())) {
                SET("logo=#{logo}");
            }
            if (StringUtils.isNotBlank(company.getIntroduction())) {
                SET("introduction=#{introduction}");
            }
            SET("utime=unix_timestamp()");
            WHERE("id=#{id}");
            return SQL();
        }

        public String getCompanyByConds(Map<String, Object> conds) {
            BEGIN();
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("company");

            if (conds.get("id") != null) {
                WHERE("id=#{id}");
            }

            if (conds.get("maxId") != null) {
                WHERE("id<#{maxId}");
            }

            if (CollectionUtils.isNotEmpty((Set<Integer>) conds.get("ids"))) {
                Set<Integer> ids = (Set<Integer>) conds.get("ids");
                WHERE("id in (" + StringUtils.join(ids, ",") + ")");
            }

            if (StringUtils.isNotBlank((String) conds.get("keyword"))) {
                WHERE("name like \"%\"#{keyword}\"%\"");
            }

            ORDER_BY("id DESC");

            String sql = SQL();
            if (conds.get("limit") != null) {
                sql += " limit #{limit}";
            }

            return sql;
        }
    }
}
