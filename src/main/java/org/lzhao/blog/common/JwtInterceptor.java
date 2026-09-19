package org.lzhao.blog.common;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 放行 OPTIONS 请求（CORS 预检）
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 2. 从请求头获取 Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("请求未携带 Token：{}", request.getRequestURI());
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录，请先登录\"}");
            return false;
        }

        String token = authHeader.substring(7);

        // 3. 验证 Token
        if (!jwtUtil.validateToken(token)) {
            log.warn("Token 无效：{}", token);
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"Token 已过期或无效\"}");
            return false;
        }

        // 4. 解析 Token，把用户信息放到请求属性里
        Claims claims = jwtUtil.parseToken(token);
        request.setAttribute("userId", claims.get("userId", Long.class));
        request.setAttribute("username", claims.get("username", String.class));

        log.info("Token 验证通过：userId={}, username={}",
                claims.get("userId"), claims.get("username"));

        return true;
    }
}