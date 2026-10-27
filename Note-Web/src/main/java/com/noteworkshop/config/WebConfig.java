package com.noteworkshop.config;

import com.noteworkshop.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置：注册拦截器
 *
 * 只拦"需要登录"的写接口；
 * 公开接口（列表、详情、搜索、登录、注册）不拦。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(
                        "/note/add",        // 发笔记
                        "/note/update",     // 改笔记
                        "/note/delete",     // 删笔记
                        "/note/my",         // 我的笔记（要看自己的，必须登录）
                        "/comment/add",     // 发评论
                        "/like/toggle",     // 点赞 / 取消
                        "/like/my"          // 我点赞过的
                );
    }
}