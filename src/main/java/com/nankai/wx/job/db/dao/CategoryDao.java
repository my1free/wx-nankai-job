package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.Category;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface CategoryDao {
    @Select("select * from category")
    List<Category> getAllCategories();

//    @SelectProvider(type = SqlProvider.class, method = "getCitiesByConds")
//    List<City> getCitiesByConds(Map<String, Object> conds);

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(Category category);

    class SqlProvider {
        public String insert(Category category) {
            BEGIN();
            INSERT_INTO("category");
            VALUES("name", "#{name}");
            VALUES("ctime", "unix_timestamp()");
            VALUES("utime", "unix_timestamp()");

            return SQL();
        }

//        public String getCitiesByConds(Map<String, Object> conds) {
//            BEGIN();
//            Set<String> fields = (Set) conds.get("fields");
//            if (!CollectionUtils.isEmpty(fields)) {
//                SELECT(StringUtils.join(fields, ","));
//            } else {
//                SELECT("*");
//            }
//            FROM("city");
//
//            if (conds.get("id") != null) {
//                WHERE("id=#{id}");
//            }
//
//            if (CollectionUtils.isNotEmpty((Set<Integer>) conds.get("ids"))) {
//                Set<Integer> ids = (Set<Integer>) conds.get("ids");
//                WHERE("id in (" + StringUtils.join(ids, ",") + ")");
//            }
//
//            return SQL();
//        }
    }
}
