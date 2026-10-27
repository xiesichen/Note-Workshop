package com.noteworkshop.controller;

import com.noteworkshop.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * 点赞 / 取消点赞（切换）
     * POST /like/toggle?noteId=1
     */
    @PostMapping("/toggle")
    public Map<String, Object> toggle(@RequestParam Integer noteId, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        boolean liked = likeService.toggle(noteId, userId);

        Map<String, Object> result = new HashMap<>();
        result.put("liked", liked);
        return result;
    }

    /**
     * 我点赞过的笔记（分页）
     * GET /like/my?page=1&size=10
     */
    @GetMapping("/my")
    public Map<String, Object> my(@RequestParam(defaultValue = "1") int page,
                                  HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        return likeService.myLikedNotes(userId, page);
    }
}
