package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/18
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface IntegratedDao {
    @Select("SELECT job.* from job LEFT JOIN company ON job.company_id=company.id " +
            "LEFT JOIN concerned on company.id=concerned.company_id " +
            "WHERE concerned.valid=1 AND concerned.user_id=#{userId} " +
            "AND job.id<#{maxId} ORDER BY job.id DESC limit #{limit}")
    List<Job> getConcernedJobList(@Param("userId") int userId,
                                  @Param("maxId") int maxId,
                                  @Param("limit") int limit);
}
