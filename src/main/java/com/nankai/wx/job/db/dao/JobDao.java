package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.Job;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

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
public interface JobDao {

    @SelectProvider(type = SqlProvider.class, method = "getJobByConds")
    List<Job> getJobByConds(Map<String, Object> conds);

    @Select("select * from job where id<#{maxId} order by id desc limit #{limit}")
    List<Job> getJobList(@Param("maxId") long maxId, @Param("limit") int limit);

    @SelectProvider(type = SqlProvider.class, method = "getJobBySearch")
    List<Job> getJobBySearch(Map<String, Object> conds);

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(Job job);

    class SqlProvider {
        public String insert(Job job) {
            BEGIN();
            INSERT_INTO("job");
            VALUES("company_id", "#{companyId}");
            VALUES("city_id", "#{cityId}");
            VALUES("title", "#{title}");
            VALUES("salary_low", "#{salaryLow}");
            VALUES("salary_high", "#{salaryHigh}");
            VALUES("status", "#{status}");
            VALUES("ctime", "unix_timestamp()");
            VALUES("utime", "unix_timestamp()");

            if (job.getCategoryId() != null && job.getCategoryId() > 0) {
                VALUES("category_id", "#{categoryId}");
            }
            if (StringUtils.isNotBlank(job.getJobAbstract())) {
                VALUES("job_abstract", "#{jobAbstract}");
            }
            if (job.getTagId() != null && job.getTagId() > 0) {
                VALUES("tag_id", "#{tagId}");
            }
            if (StringUtils.isNotBlank(job.getDescription())) {
                VALUES("description", "#{description}");
            }
            if (StringUtils.isNotBlank(job.getLocation())) {
                VALUES("location", "#{location}");
            }
            if (job.getPublisher() != null && job.getPublisher() > 0) {
                VALUES("publisher", "#{publisher}");
            }
            return SQL();
        }

        public String getJobByConds(Map<String, Object> conds) {
            BEGIN();
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("job");

            if (conds.get("id") != null) {
                WHERE("id=#{id}");
            }

            if (CollectionUtils.isNotEmpty((Set<Integer>) conds.get("ids"))) {
                Set<Integer> ids = (Set<Integer>) conds.get("ids");
                WHERE("id in (" + StringUtils.join(ids, ",") + ")");
            }
            if (conds.get("companyId") != null && ((int) conds.get("companyId")) > 0) {
                WHERE("company_id=#{companyId}");
            }
            if (conds.get("categoryId") != null && ((int) conds.get("categoryId")) > 0) {
                WHERE("category_id=#{categoryId}");
            }
            if (conds.get("cityId") != null && ((int) conds.get("cityId")) > 0) {
                WHERE("city_id=#{cityId}");
            }
            if (conds.get("maxId") != null && ((int) conds.get("maxId")) > 0) {
                WHERE("id<#{maxId}");
            }
            if (StringUtils.isNotBlank((String) conds.get("companyIdsStr"))) {
                WHERE("company_id in ( #{companyIdsStr} )");
            }

            ORDER_BY("id desc");

            String sql = SQL();

            if (conds.get("limit") != null && ((int) conds.get("maxId")) > 0) {
                sql += " limit #{limit}";
            }

            return sql;
        }

        public String getJobBySearch(Map<String, Object> conds) {
            BEGIN();
            SELECT("j.*");
            FROM("job j");
            LEFT_OUTER_JOIN("city c on j.city_id=c.id");
            LEFT_OUTER_JOIN("company co on j.company_id=co.id");
            if (StringUtils.isNotBlank((String) conds.get("keyword"))) {
                WHERE("(j.title like \"%\"#{keyword}\"%\" or co.name like \"%\"#{keyword}\"%\")");
            }
            if (conds.get("cityId") != null && ((int) conds.get("cityId")) > 0) {
                WHERE("j.city_id=#{cityId}");
            }

            if (conds.get("maxId") != null && ((int) conds.get("maxId")) > 0) {
                WHERE("j.id<#{maxId}");
            }

            ORDER_BY("id desc");

            String sql = SQL();

            if (conds.get("limit") != null && ((int) conds.get("maxId")) > 0) {
                sql += " limit #{limit}";
            }

            return sql;
        }
    }
}
