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
@TableName("tb_course_selection")
public class CourseSelection implements Serializable {

    @TableId
    private String id;

    @TableField("student_id")
    private String studentId;

    @TableField("course_id")
    private String courseId;

    @TableField("score")
    private Double score;

    @TableField("evaluation")
    private String evaluation;

    @TableField("selection_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date selectionTime;

    @TableField("createtime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    @TableField(exist = false)
    private String studentName;

    @TableField(exist = false)
    private String courseName;

}
