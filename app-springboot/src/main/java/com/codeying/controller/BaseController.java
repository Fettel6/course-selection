package com.codeying.controller;

import com.codeying.component.ApiResult;
import com.codeying.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/** 项目Controller通用父类 */
public class BaseController {

  @Autowired protected HttpServletRequest req;

  @Autowired protected HttpServletResponse resp;

  @Autowired protected HttpSession session;

  // 获取当前登陆用户
  protected LoginUser getCurrentUser() {
    return (LoginUser) getSessionValue("user");
  }
  /**
   * 获取Session中的值
   *
   * @param key key
   * @return value
   */
  protected Object getSessionValue(String key) {
    return this.session.getAttribute(key);
  }

  /**
   * 设置Session中的值
   *
   * @param key key
   * @param value value
   */
  protected void setSessionValue(String key, Object value) {
    this.session.setAttribute(key, value);
  }

  protected <T> ApiResult<T> success() {
    return ApiResult.success();
  }

  protected <T> ApiResult<T> fail() {
    return ApiResult.fail();
  }

  protected <T> ApiResult<T> fail(String message) {
    return ApiResult.fail(message);
  }

}

