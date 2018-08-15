package com.nankai.wx.job.db.dao;

import com.nankai.wx.job.db.domain.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/7/8
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Mapper
public interface FeedbackDao {
    @Insert("INSERT INTO feedback(user_id, feedback, ctime, utime) " +
            "VALUES(#{userId}, #{feedback}, unix_timestamp(), unix_timestamp())")
    int insert(Feedback feedback);

    @Select("SELECT * FROM feedback ORDER BY id DESC")
    List<Feedback> getAllFeedback();
}
