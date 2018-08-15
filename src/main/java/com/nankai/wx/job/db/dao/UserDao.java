package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.User;
import com.nankai.wx.job.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/3
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface UserDao {
    @Select("select * from user")
    List<User> getAllUsers();

    @UpdateProvider(type = SqlProvider.class, method = "updateUser")
    int updateUser(User user);

    @Options(useGeneratedKeys = true)
    @Insert("insert into user(openid, unionid, name, avatar, type, ctime, utime) " +
            " values(#{openid}, #{unionid}, #{name}, #{avatar}, 0, unix_timestamp(), unix_timestamp())" +
            " ON DUPLICATE KEY UPDATE name=#{name}, avatar=#{avatar}, utime=unix_timestamp()")
    int insertOrUpdate(User user);

    @SelectProvider(type = SqlProvider.class, method = "getUserByConds")
    List<User> getUserByConds(Map<String, Object> conds);

    @Update("UPDATE user SET type=#{type} WHERE id=#{userId}")
    int updateUserType(@Param("userId") int userId, @Param("type") int type);

    @Select("SELECT type FROM user WHERE id=#{userId}")
    Integer getUserType(@Param("userId") int userId);

    class SqlProvider {
        public String getUserByConds(Map<String, Object> conds) {
            BEGIN();
            Set<String> fields = (Set) conds.get("fields");
            if (!CollectionUtils.isEmpty(fields)) {
                SELECT(StringUtils.join(fields, ","));
            } else {
                SELECT("*");
            }
            FROM("user");
            if (!NumberUtil.isNullOrZero((Integer) conds.get("id"))) {
                WHERE("id=#{id}");
            }
            if (StringUtils.isNotBlank((String) conds.get("openid"))) {
                WHERE("openid=#{openid}");
            }
            if (CollectionUtils.isNotEmpty((Set<String>) conds.get("openids"))) {
                Set<String> openids = (Set<String>) conds.get("openids");
                WHERE("openid in (" + StringUtils.join(openids, ",") + ")");
            }
            return SQL();
        }

        public String updateUser(User user) {
            BEGIN();
            UPDATE("user");
            if (StringUtils.isNotBlank(user.getName())) {
                SET("name=#{name}");
            }
            if (StringUtils.isNotBlank(user.getAvatar())) {
                SET("avatar=#{avatar}");
            }
            if (StringUtils.isNotBlank(user.getEmail())) {
                SET("email=#{email}");
            }
            if (StringUtils.isNotBlank(user.getMobile())) {
                SET("mobile=#{mobile}");
            }
            if (StringUtils.isNotBlank(user.getWechat())) {
                SET("wechat=#{wechat}");
            }
            SET("utime=unix_timestamp()");
            WHERE("id=#{id}");
            return SQL();
        }
    }
}
