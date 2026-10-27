package com.noteworkshop.mapper;

import com.noteworkshop.pojo.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    /**
     * 查询所有未删除的笔记，按创建时间倒序
     */
    @Select("select * from note where deleted = 0 and is_public = 1 order by create_time desc")
    List<Note> findAll();

    @Update("update note set deleted = 1 where id = #{noteId}")
    void deleteById(Integer noteId);

    @Insert("insert into note (user_id, book_name, title, content, is_public) " +
            "values (#{userId}, #{bookName}, #{title}, #{content}, #{isPublic})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Note note);

    @Select("select * from note where id = #{id} and deleted = 0")
    Note findById(Integer id);
    /**
     * 修改笔记：只改标题、内容、公开状态
     */
    @Update("update note set book_name = #{bookName}, title = #{title}, " +
            "content = #{content}, is_public = #{isPublic} " +
            "where id = #{id} and deleted = 0")
    int update(Note note);


    /**
     * 按关键字搜索（标题或内容包含关键字）
     */
    @Select("select * from note where deleted = 0 and is_public = 1 " +
            "and (title like concat('%', #{kw}, '%') " +
            "     or content like concat('%', #{kw}, '%')) " +
            "order by create_time desc")
    List<Note> search(String kw);
}
