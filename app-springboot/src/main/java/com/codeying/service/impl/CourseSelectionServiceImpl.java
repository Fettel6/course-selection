package com.codeying.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeying.mapper.CourseSelectionMapper;
import com.codeying.entity.CourseSelection;
import com.codeying.service.CourseSelectionService;
import org.springframework.stereotype.Service;

@Service
public class CourseSelectionServiceImpl extends ServiceImpl<CourseSelectionMapper, CourseSelection> implements CourseSelectionService {}
