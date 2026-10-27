package com.noteworkshop.interceptor;

import com.noteworkshop.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 *
 * 作用：检查请求头里的 JWT，没登录/令牌无效 → 返回 401，不放行
 *
 * 生效范围：由 WebConfig 指定（只拦写接口，公开接口不拦）
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在 Controller 方法执行【之前】调用
     *
     * @return true = 放行，请求继续走；false = 拦下，请求到此为止
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // ① 取请求头 Authorization
        String auth = request.getHeader("Authorization");

        // ② 没有，或者不是 "Bearer " 开头 → 401
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.warn("未登录访问：{} {}", request.getMethod(), request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);   // 401
            return false;                                              // 不放行
        }

        // ③ 去掉 "Bearer " 前缀，得到纯 token
        String token = auth.substring(7);
        //                        └┬┘
        //               "Bearer " 正好 7 个字符

        // ④ 解析 token
        Integer userId = jwtUtil.parseUserId(token);

        // ⑤ 无效 / 过期 → 401
        if (userId == null) {
            log.warn("令牌无效或已过期：{} {}", request.getMethod(), request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // ⑥ 有效 → 把 userId 放进 request，Controller 可以直接取
        request.setAttribute("userId", userId);
        log.info("拦截器放行，userId={}", userId);
        return true;
    }
}