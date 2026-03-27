package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.component.ApiResult;
import com.codeying.component.PagerFooterVO;
import com.codeying.entity.Course;
import com.codeying.entity.CourseSelection;
import com.codeying.entity.Student;
import com.codeying.service.CourseSelectionService;
import com.codeying.service.CourseService;
import com.codeying.service.StudentService;
import com.codeying.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("courseSelection")
public class CourseSelectionController extends BaseController {

    @Autowired
    protected CourseSelectionService courseSelectionService;

    @Autowired
    protected CourseService courseService;

    @Autowired
    protected StudentService studentService;

    @RequestMapping("list")
    public String list(Model model, Integer pageIndex, Integer size, String studentId, String courseId) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        if (size == null) {
            size = 15;
        }
        QueryWrapper<CourseSelection> paramMap = new QueryWrapper<>();
        paramMap.eq(!StringUtils.isEmpty(studentId), "student_id", studentId);
        paramMap.eq(!StringUtils.isEmpty(courseId), "course_id", courseId);
        paramMap.orderByAsc("id");
        IPage<CourseSelection> pageInfo = new Page<CourseSelection>().setCurrent(pageIndex).setSize(size);
        pageInfo = courseSelectionService.page(pageInfo, paramMap);

        List<CourseSelection> list = pageInfo.getRecords();
        for (CourseSelection cs : list) {
            Student student = studentService.getById(cs.getStudentId());
            Course course = courseService.getById(cs.getCourseId());
            if (student != null) {
                cs.setStudentName(student.getName());
            }
            if (course != null) {
                cs.setCourseName(course.getName());
            }
        }

        model.addAttribute("courseSelectionList", list);
        model.addAttribute("pager", new PagerFooterVO(pageInfo));
        model.addAttribute("studentId", studentId);
        model.addAttribute("courseId", courseId);
        return "pages/course-selection-list";
    }

    @RequestMapping("edit")
    public String edit(String id, Model model) {
        CourseSelection entity = courseSelectionService.getById(id);
        Student student = studentService.getById(entity.getStudentId());
        Course course = courseService.getById(entity.getCourseId());
        if (student != null) {
            entity.setStudentName(student.getName());
        }
        if (course != null) {
            entity.setCourseName(course.getName());
        }
        model.addAttribute("item", entity);
        return "pages/course-selection-edit";
    }

    @RequestMapping("detail")
    public String detail(String id, Model model) {
        CourseSelection entity = courseSelectionService.getById(id);
        Student student = studentService.getById(entity.getStudentId());
        Course course = courseService.getById(entity.getCourseId());
        if (student != null) {
            entity.setStudentName(student.getName());
        }
        if (course != null) {
            entity.setCourseName(course.getName());
        }
        model.addAttribute("item", entity);
        return "pages/course-selection-detail";
    }

    @RequestMapping("save")
    @ResponseBody
    public ApiResult<Object> save(CourseSelection entityTemp) {
        courseSelectionService.updateById(entityTemp);
        return ApiResult.successMsg("保存成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ApiResult<Object> delete(String id) {
        CourseSelection cs = courseSelectionService.getById(id);
        // 学生只能删除自己的选课
        if (getCurrentUser().getRole().equals("student") && !cs.getStudentId().equals(getCurrentUser().getId())) {
            return fail("无权删除他人的选课记录");
        }
        boolean res = courseSelectionService.removeById(id);
        return res ? success() : fail();
    }

    @RequestMapping("myCourses")
    public String myCourses(Model model, Integer pageIndex, Integer size) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        if (size == null) {
            size = 15;
        }
        String studentId = getCurrentUser().getId();
        QueryWrapper<CourseSelection> paramMap = new QueryWrapper<>();
        paramMap.eq("student_id", studentId);
        paramMap.orderByAsc("id");
        IPage<CourseSelection> pageInfo = new Page<CourseSelection>().setCurrent(pageIndex).setSize(size);
        pageInfo = courseSelectionService.page(pageInfo, paramMap);

        List<CourseSelection> list = pageInfo.getRecords();
        for (CourseSelection cs : list) {
            Course course = courseService.getById(cs.getCourseId());
            if (course != null) {
                cs.setCourseName(course.getName());
            }
        }

        model.addAttribute("courseSelectionList", list);
        model.addAttribute("pager", new PagerFooterVO(pageInfo));
        return "pages/course-selection-mine";
    }

    @RequestMapping("selectCourse")
    public String selectCourse(Model model, Integer pageIndex, Integer size, String courseno, String name, String teacher) {
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
        return "pages/course-select";
    }

    @RequestMapping("doSelect")
    @ResponseBody
    public ApiResult<Object> doSelect(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            return fail("请选择课程");
        }
        Course course = courseService.getById(courseId);
        if (course == null) {
            return fail("课程不存在");
        }

        String studentId = getCurrentUser().getId();
        QueryWrapper<CourseSelection> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        wrapper.eq("course_id", courseId);
        if (!courseSelectionService.list(wrapper).isEmpty()) {
            return fail("您已经选择过这门课程了");
        }

        CourseSelection cs = new CourseSelection();
        cs.setId(CommonUtils.newId());
        cs.setStudentId(studentId);
        cs.setCourseId(courseId);
        cs.setSelectionTime(new Date());
        cs.setCreatetime(new Date());
        courseSelectionService.save(cs);
        return ApiResult.successMsg("选课成功");
    }

}
