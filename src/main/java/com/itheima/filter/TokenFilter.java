package com.itheima.filter;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //1. 获取请求url。
        String url = ((HttpServletRequest) request).getRequestURI();

        //2. 判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if (url.contains("/login")) {
            log.info("放行");
            chain.doFilter(request,response);
            return;
        }

        //3. 获取请求头中的令牌（token）。
        String token = ((HttpServletRequest) request).getHeader("token");

        //4. 判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if (token == null || token.isEmpty()) {
            log.info("令牌为空,响应401");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //5. 解析token，如果解析失败，返回错误结果（未登录）。
        try {
            JwtUtils.parseJWT(token);
        } catch (Exception e) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //6. 放行。
        log.info("令牌合法，放行");
        chain.doFilter(request,response);
    }
}
