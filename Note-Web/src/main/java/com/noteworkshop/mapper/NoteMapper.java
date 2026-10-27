package com.noteworkshop.mapper;

import com.noteworkshop.pojo.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 说明：所有"查笔记"的 SQL 都带上了点赞数子查询
 *
 *   (select count(*) from note_like l where l.note_id = n.id) as likeCount
 *
 * 别名 likeCount 要和 Note 实体的 likeCount 字段对上，MyBatis 才会填进对象。
 * 表名加了别名 n，因为有子查询，必须区分内外层。
 */
@Mapper
public interface NoteMapper {

    // ==================== 查 ====================

    /** 大厅：所有公开笔记（带点赞数），按创建时间倒序 */
    @Select("select n.*, " +
            "  (select count(*) from note_like l where l.note_id = n.id) as likeCount " +
            "from note n " +
            "where n.deleted = 0 and n.is_public = 1 " +
            "order by n.create_time desc")
    List<Note> findAll();

    /** 详情（带点赞数） */
    @Select("select n.*, " +
            "  (select count(*) from note_like l where l.note_id = n.id) as likeCount " +
            "from note n " +
            "where n.id = #{id} and n.deleted = 0")
    Note findById(Integer id);

    /** 按关键字搜索（带点赞数） */
    @Select("select n.*, " +
            "  (select count(*) from note_like l where l.note_id = n.id) as likeCount " +
            "from note n " +
            "where n.deleted = 0 and n.is_public = 1 " +
            "  and (n.title like concat('%', #{kw}, '%') " +
            "       or n.content like concat('%', #{kw}, '%')) " +
            "order by n.create_time desc")
    List<Note> search(String kw);

    /** 我的笔记（含私有，分页，带点赞数） */
    @Select("select n.*, " +
            "  (select count(*) from note_like l where l.note_id = n.id) as likeCount " +
            "from note n " +
            "where n.user_id = #{userId} and n.deleted = 0 " +
            "order by n.create_time desc " +
            "limit #{size} offset #{offset}")
    List<Note> findByUserId(@Param("userId") Integer userId,
                            @Param("offset") int offset,
                            @Param("size") int size);

    /** 我的笔记总数 */
    @Select("select count(*) from note where user_id = #{userId} and deleted = 0")
    int countByUserId(Integer userId);

    // ==================== 点赞相关 ====================

    /**
     * 我点赞过的笔记（分页，带点赞数）
     *
     * 和 note_like 关联，只查我点过赞的、还在的、公开的笔记
     */
    @Select("select n.*, " +
            "  (select count(*) from note_like l2 where l2.note_id = n.id) as likeCount " +
            "from note_like l " +
            "join note n on n.id = l.note_id " +
            "where l.user_id = #{userId} and n.deleted = 0 " +
            "order by l.create_time desc " +
            "limit #{size} offset #{offset}")
    List<Note> findLikedByUserId(@Param("userId") Integer userId,
                                 @Param("offset") int offset,
                                 @Param("size") int size);

    /** 我点赞过的笔记总数 */
    @Select("select count(*) from note_like l join note n on n.id = l.note_id " +
            "where l.user_id = #{userId} and n.deleted = 0")
    int countLikedByUserId(Integer userId);

    /**
     * 热门榜：所有公开笔记按点赞数排名（分页，带点赞数）
     *
     * order by likeCount desc           → 点赞多的在前
     *        , n.create_time desc       → 点赞数相同时按时间兜底（保证顺序稳定）
     */
    @Select("select n.*, " +
            "  (select count(*) from note_like l where l.note_id = n.id) as likeCount " +
            "from note n " +
            "where n.deleted = 0 and n.is_public = 1 " +
            "order by likeCount desc, n.create_time desc " +
            "limit #{size} offset #{offset}")
    List<Note> findHot(@Param("offset") int offset, @Param("size") int size);

    /** 热门榜总数 */
    @Select("select count(*) from note where deleted = 0 and is_public = 1")
    int countPublic();

    // ==================== 写 ====================

    @Insert("insert into note (user_id, book_name, title, content, is_public) " +
            "values (#{userId}, #{bookName}, #{title}, #{content}, #{isPublic})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Note note);

    @Update("update note set book_name = #{bookName}, title = #{title}, " +
            "content = #{content}, is_public = #{isPublic} " +
            "where id = #{id} and deleted = 0")
    int update(Note note);

    @Update("update note set deleted = 1 where id = #{noteId}")
    void deleteById(Integer noteId);
}
