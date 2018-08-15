package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.CompanyBelonged;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

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
public interface CompanyBelongedDao {

    @SelectProvider(type = SqlProvider.class, method = "select")
    List<CompanyBelonged> getBelongedCompanies(Map<String, Object> conds);

    @Select("SELECT * FROM company_belonged WHERE user_id=#{userId} AND company_id=#{companyId}")
    CompanyBelonged checkExist(@Param("userId") int userId, @Param("companyId") int companyId);

    @Update("UPDATE company_belonged SET valid=1 WHERE id=#{id}")
    int setValid(int id);

    @Update("UPDATE company_belonged SET valid=0 WHERE id=#{id}")
    int setInValid(int id);

    @Options(useGeneratedKeys = true)
    @Insert("INSERT IGNORE INTO company_belonged(company_id, user_id, valid, ctime, utime) VALUES(" +
            "#{companyId}, #{userId}, 1, unix_timestamp(), unix_timestamp())")
    int insert(CompanyBelonged belonged);

    class SqlProvider {
        public String insert(CompanyBelonged belonged) {
            BEGIN();
            INSERT_INTO("company_belonged");
            VALUES("company_id", "#{companyId}");
            VALUES("user_id", "#{userId}");
            VALUES("valid", "1");
            VALUES("ctime", "unix_timestamp()");
            VALUES("utime", "unix_timestamp()");
            return SQL();
        }

        public String select(Map<String, Object> conds) {
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("company_belonged");
            if (conds.get("userId") != null) {
                WHERE("user_id=#{userId}");
            }
            if (conds.get("companyId") != null) {
                WHERE("company_id=#{companyId}");
            }
            if (CollectionUtils.isNotEmpty((Set) conds.get("companyIds"))) {
                Set<Integer> companyIds = (Set<Integer>) conds.get("companyIds");
                WHERE("company_id in (" + StringUtils.join(companyIds, ",") + ")");
            }
            if (conds.get("valid") != null) {
                WHERE("valid=#{valid}");
            }else {
                WHERE("valid=1");
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
