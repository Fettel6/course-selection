package com.codeying.component.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.codeying.component.servlet.CaptchaServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * 配置类
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    CaptchaServlet captchaServlet;
    @Autowired
    LoginInterceptor loginInterceptor;

    /**
     * 除了登录页面，所有页面都要验证是否登录
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/hello","/admin/**","/student/**");//需要拦截的页面
    }

    /**
     * 将验证码添加到配置
     * @return
     */
    @Bean
    public ServletRegistrationBean<CaptchaServlet> registerCaptchaServlet() {
        ServletRegistrationBean<CaptchaServlet> bean = new ServletRegistrationBean<>();
        bean.setServlet(captchaServlet);
        bean.setUrlMappings(Collections.singletonList("/captcha"));
        return bean;
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
