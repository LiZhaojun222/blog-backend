package org.lzhao.blog.config;

import org.lzhao.blog.common.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Lzj
 * @create 2026-09-15 16:10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")             // 拦截所有 /api 请求
                .excludePathPatterns(                   // 排除不需要登录的接口
                        "/api/user/login",              // 登录接口
                        "/api/user/register",           // 注册接口
                        "/api/user/hello",              // 测试接口
                        "/api/article/list",            // 文章列表公开
                        "/api/article/detail/*",        // 文章详情公开
                        "/api/category/list",          // 分类列表公开
                        "/api/tag/list",
                        // ✅ Knife4j 相关路径
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                );
    }

    /**
     * ✅ CORS 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)               // 允许携带 Cookie
                .maxAge(3600);                        // 预检请求缓存 1 小时
    }
}
