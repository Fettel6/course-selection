package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.component.ApiResult;
import com.codeying.component.PagerFooterVO;
import com.codeying.entity.Course;
import com.codeying.service.CourseService;
import com.codeying.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("course")
public class CourseController extends BaseController {

    @Autowired
    protected CourseService courseService;

    @RequestMapping("list")
    public String list(Model model, Integer pageIndex, Integer size, String courseno, String name, String teacher) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        if (size == null) {
            size = 15;
        }
        QueryWrapper<Course> paramMap = new QueryWrapper<>();
        paramMap.like(!StringUtils.isEmpty(courseno), "courseno", courseno);
        paramMap.like(!StringUtils.isEmpty(name), "name", name);
        paramMap.like(!StringUtils.isEmpty(teacher), "teacher", teacher);
        paramMap.orderByAsc("id");
        IPage<Course> pageInfo = new Page<Course>().setCurrent(pageIndex).setSize(size);
        pageInfo = courseService.page(pageInfo, paramMap);
        model.addAttribute("courseList", pageInfo.getRecords());
        model.addAttribute("pager", new PagerFooterVO(pageInfo));
        model.addAttribute("courseno", courseno);
        model.addAttribute("name", name);
        model.addAttribute("teacher", teacher);
        return "pages/course-list";
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            return "pages/course-add";
        }
        Course entity = courseService.getById(id);
        model.addAttribute("item", entity);
        return "pages/course-edit";
    }

    @RequestMapping("detail")
    public String detail(String id, Model model) {
        Course entity = courseService.getById(id);
        model.addAttribute("item", entity);
        return "pages/course-detail";
    }

    @RequestMapping("save")
    @ResponseBody
    public ApiResult<Object> save(Course entityTemp) {
        String id = entityTemp.getId();
        if (id == null || id.isEmpty()) {
            entityTemp.setId(CommonUtils.newId());
            entityTemp.setCreatetime(new Date());
            QueryWrapper<Course> wrapperCourseno = new QueryWrapper<>();
            wrapperCourseno.eq("courseno", entityTemp.getCourseno());
            if (!courseService.list(wrapperCourseno).isEmpty()) {
                return fail("课程号 已存在！");
            }
            courseService.save(entityTemp);
        } else {
            courseService.updateById(entityTemp);
        }
        return ApiResult.successMsg("保存成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ApiResult<Object> delete(String id) {
        boolean res = courseService.removeById(id);
        return res ? success() : fail();
    }

}
