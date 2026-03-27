package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.component.ApiResult;
import com.codeying.component.PagerFooterVO;
import com.codeying.entity.Student;
import com.codeying.service.StudentService;
import com.codeying.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("student")
public class StudentController extends BaseController {

    @Autowired
    protected StudentService studentService;

    @RequestMapping("list")
    public String list(Model model, Integer pageIndex, Integer size, String username, String studentno, String name, String classname) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        if (size == null) {
            size = 15;
        }
        QueryWrapper<Student> paramMap = new QueryWrapper<>();
        paramMap.like(!StringUtils.isEmpty(username), "username", username);
        paramMap.like(!StringUtils.isEmpty(studentno), "studentno", studentno);
        paramMap.like(!StringUtils.isEmpty(name), "name", name);
        paramMap.like(!StringUtils.isEmpty(classname), "classname", classname);
        paramMap.orderByAsc("id");
        IPage<Student> pageInfo = new Page<Student>().setCurrent(pageIndex).setSize(size);
        pageInfo = studentService.page(pageInfo, paramMap);
        model.addAttribute("studentList", pageInfo.getRecords());
        model.addAttribute("pager", new PagerFooterVO(pageInfo));
        model.addAttribute("username", username);
        model.addAttribute("studentno", studentno);
        model.addAttribute("name", name);
        model.addAttribute("classname", classname);
        return "pages/student-list";
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            return "pages/student-add";
        }
        Student entity = studentService.getById(id);
        model.addAttribute("item", entity);
        return "pages/student-edit";
    }

    @RequestMapping("detail")
    public String detail(String id, Model model) {
        Student entity = studentService.getById(id);
        model.addAttribute("item", entity);
        return "pages/student-detail";
    }

    @RequestMapping("save")
    @ResponseBody
    public ApiResult<Object> save(Student entityTemp) {
        String id = entityTemp.getId();
        if (id == null || id.isEmpty()) {
            entityTemp.setId(CommonUtils.newId());
            entityTemp.setCreatetime(new Date());
            QueryWrapper<Student> wrapperUsername = new QueryWrapper<>();
            wrapperUsername.eq("username", entityTemp.getUsername());
            if (!studentService.list(wrapperUsername).isEmpty()) {
                return fail("用户名 已存在！");
            }
            QueryWrapper<Student> wrapperStudentno = new QueryWrapper<>();
            wrapperStudentno.eq("studentno", entityTemp.getStudentno());
            if (!studentService.list(wrapperStudentno).isEmpty()) {
                return fail("学号 已存在！");
            }
            studentService.save(entityTemp);
        } else {
            studentService.updateById(entityTemp);
            if (getCurrentUser().getId().equals(id)) {
                setSessionValue("user", studentService.getById(id));
            }
        }
        return ApiResult.successMsg("保存成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ApiResult<Object> delete(String id) {
        boolean res = studentService.removeById(id);
        return res ? success() : fail();
    }

}
