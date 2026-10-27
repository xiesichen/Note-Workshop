package com.noteworkshop.mapper;

import com.noteworkshop.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 查询某篇笔记的所有评论（JOIN user 表拿显示名）
     *
     * 关键点：
     *   u.username as userName
     *   → 把 user 表的 username 列，起别名 userName
     *   → 这样 MyBatis 才能调 comment.setUserName() 填进实体
     *
     * left join：即使作者被删了，评论也能显示（userName 为 null）
     */
    @Select("select c.*, u.username as userName " +
            "from comment c " +
            "left join user u on c.user_id = u.id " +
            "where c.note_id = #{noteId} and c.deleted = 0 " +
            "order by c.create_time asc")
    List<Comment> findByNoteId(Integer noteId);


    /**
     * 新增一条评论
     *
     * @Options：插入后把数据库生成的自增 id 回填到 comment.getId()
     */
    @Insert("insert into comment (note_id, user_id, parent_id, content) " +
            "values (#{noteId}, #{userId}, #{parentId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Comment comment);
}