package com.noteworkshop.service;

import java.util.Map;

public interface LikeService {

    /**
     * 切换点赞状态
     *
     * @return true = 刚点赞成功；false = 刚取消点赞
     */
    boolean toggle(Integer noteId, Integer userId);

    /**
     * 我点赞过的笔记（分页，每页固定 5 条）
     *
     * @return {"list": [...], "total": 总数, "page": 当前页, "size": 每页条数, "pages": 总页数}
     */
    Map<String, Object> myLikedNotes(Integer userId, int page);
}
