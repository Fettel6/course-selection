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
@TableName("tb_course")
public class Course implements Serializable {

    @TableId
    private String id;

    @TableField("courseno")
    private String courseno;

    @TableField("name")
    private String name;

    @TableField("credit")
    private Double credit;

    @TableField("teacher")
    private String teacher;

    @TableField("description")
    private String description;

    @TableField("createtime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

}
