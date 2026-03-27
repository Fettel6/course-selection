package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

import java.io.Serializable;

@Data
@TableName("tb_student")
public class Student extends LoginUser implements Serializable {

    @TableField(exist = false)
    String role = "student";
    @TableField(exist = false)
    String rolech = "学生";

    @TableId
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("studentno")
    private String studentno;

    @TableField("name")
    private String name;

    @TableField("gender")
    private String gender;

    @TableField("classname")
    private String classname;

    @TableField("nativeplace")
    private String nativeplace;

    @TableField("tele")
    private String tele;

    @TableField("createtime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

}
