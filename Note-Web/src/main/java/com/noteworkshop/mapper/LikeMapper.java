package com.noteworkshop.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface LikeMapper {

    /** 查这个用户对这篇笔记点过赞没有（返回 0 或 1） */
    @Select("select count(*) from note_like where note_id = #{noteId} and user_id = #{userId}")
    int exists(@Param("noteId") Integer noteId, @Param("userId") Integer userId);

    /** 点赞（同一对 note_id+user_id 有唯一索引，重复插入会报错） */
    @Insert("insert into note_like (note_id, user_id) values (#{noteId}, #{userId})")
    int insert(@Param("noteId") Integer noteId, @Param("userId") Integer userId);

    /** 取消点赞 */
    @Delete("delete from note_like where note_id = #{noteId} and user_id = #{userId}")
    int delete(@Param("noteId") Integer noteId, @Param("userId") Integer userId);
}
