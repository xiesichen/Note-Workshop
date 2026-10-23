package com.noteworkshop.mapper;

import com.noteworkshop.pojo.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoteMapper {

    /**
     * 查询所有未删除的笔记，按创建时间倒序
     */
    @Select("select * from note where deleted = 0 and is_public = 1 order by create_time desc")
    List<Note> findAll();
}
