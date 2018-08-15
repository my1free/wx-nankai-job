package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.CompanyModifier;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/8/1
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface CompanyModifierDao {

    @Select("SELECT * FROM company_modifier WHERE company_id=#{companyId}")
    List<CompanyModifier> getModifiers(Integer companyId);

    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(CompanyModifier modifier);

    class SqlProvider{
        public String insert(CompanyModifier modifier) {
            BEGIN();
            INSERT_INTO("company_modifier");
            VALUES("company_id", "#{companyId}");
            VALUES("user_id", "#{userId}");
            VALUES("ctime", "unix_timestamp()");
            VALUES("utime", "unix_timestamp()");
            return SQL();
        }
    }
}
