package com.codeying.component.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * 防止没有登录而访问
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从Session种获取登录的用户
        Object user = request.getSession().getAttribute("user");
        //如果用户为空则叫他去登录
        if(user == null){
            response.sendRedirect("/login");
            return false;
        }
        //用户不为空，则放行
        return true;
    }

}
