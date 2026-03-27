package com.codeying.component.config;

import com.codeying.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 全局错误处理
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object myHandler(Exception e, Model model) {
        //错误日志
        logger.error("发生错误",e);
        //判断AJax请求来响应数据。
        String xRequestedWith = req.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) { //ajax
            return fail("系统繁忙，请稍后再试或联系管理员");
        } else {
            return "<html><body><script>alert('系统繁忙，请稍后再试或联系管理员');window.location.href='/'</script></body></html>";
        }
    }

}
