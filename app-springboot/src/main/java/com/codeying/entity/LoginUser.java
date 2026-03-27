package com.codeying.entity;

import lombok.Data;

/**
 * 标记为可登录用户
 */
@Data
public class LoginUser {

    protected String id;
    protected String username;
    protected String password;
    protected String role;
    protected String rolech;

}
