package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codeying.utils.CommonUtils;
import com.codeying.entity.Admin;
import com.codeying.entity.Student;
import com.codeying.entity.LoginUser;
import com.codeying.entity.Course;
import com.codeying.entity.CourseSelection;
import com.codeying.service.AdminService;
import com.codeying.service.StudentService;
import com.codeying.service.CourseService;
import com.codeying.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

/** 登陆、注册、登出 */
@Controller
public class IndexController extends BaseController {

  @Autowired
  protected AdminService adminService;

  @Autowired
  protected StudentService studentService;

  @Autowired
  protected CourseService courseService;

  @Autowired
  protected CourseSelectionService courseSelectionService;

  @RequestMapping("/")
  public String index() {
    LoginUser user = getCurrentUser();
    if (user == null) return "login";
    return "redirect:/hello";
  }

  @RequestMapping("hello")
  public String hello(Model model) {
    // 获取课程列表
    List<Course> courses = courseService.list();
    List<Map<String, Object>> courseScoreData = new ArrayList<>();
    
    // 统计每个课程的分数分布
    for (Course course : courses) {
      Map<String, Object> courseData = new HashMap<>();
      courseData.put("courseName", course.getName());
      
      // 统计分数区间
      int count0_60 = 0;   // <60分
      int count60_70 = 0;  // 60-70分
      int count70_80 = 0;  // 70-80分
      int count80_90 = 0;  // 80-90分
      int count90_100 = 0; // 90-100分
      
      // 查询该课程的所有选课记录
      QueryWrapper<CourseSelection> wrapper = new QueryWrapper<>();
      wrapper.eq("course_id", course.getId());
      List<CourseSelection> selections = courseSelectionService.list(wrapper);
      
      for (CourseSelection selection : selections) {
        Double score = selection.getScore();
        if (score != null) {
          if (score < 60) {
            count0_60++;
          } else if (score >= 60 && score < 70) {
            count60_70++;
          } else if (score >= 70 && score < 80) {
            count70_80++;
          } else if (score >= 80 && score < 90) {
            count80_90++;
          } else if (score >= 90 && score <= 100) {
            count90_100++;
          }
        }
      }
      
      courseData.put("count0_60", count0_60);
      courseData.put("count60_70", count60_70);
      courseData.put("count70_80", count70_80);
      courseData.put("count80_90", count80_90);
      courseData.put("count90_100", count90_100);
      
      courseScoreData.add(courseData);
    }
    
    model.addAttribute("courseScoreData", courseScoreData);
    return "hello";
  }

  @GetMapping("register")
  public String register() {
    return "register";
  }

  @GetMapping("login")
  public String login() {
    return "login";
  }

  @PostMapping("login")
  public String login(String captcha, String username, String password, String usertype)
      throws Exception {
    req.setCharacterEncoding("utf-8");
    // 校验验证码
    String captchaOrigin = (String) req.getSession().getAttribute("captcha");
    if (captcha == null || !captcha.equalsIgnoreCase(captchaOrigin)) {
      req.setAttribute("message", "验证码错误！");
      return "login";
    }
    // 登录开始
    LoginUser loginUser;
    if (usertype.equals("admin")) {
      QueryWrapper<Admin> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      wrapper.eq("password", password);
      loginUser = adminService.getOne(wrapper);
      if (loginUser != null) {
        req.getSession().setAttribute("user", loginUser);
        req.getSession().setAttribute("role", "admin");
        return "redirect:/hello";
      }
    } else if (usertype.equals("student")) {
      QueryWrapper<Student> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      wrapper.eq("password", password);
      loginUser = studentService.getOne(wrapper);
      if (loginUser != null) {
        req.getSession().setAttribute("user", loginUser);
        req.getSession().setAttribute("role", "student");
        return "redirect:/hello";
      }
    }
    // 登陆失败，就重新登陆
    req.setAttribute("message", "账号密码有误，登陆失败");
    return "login";
  }

  @PostMapping("register")
  public String register(String username, String password, String studentno, String name, String usertype) throws Exception {
    req.setCharacterEncoding("utf-8");
    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      req.setAttribute("message", "账号密码不可为空！");
      return "register";
    }
    if (usertype.equals("student")) {
      QueryWrapper<Student> wrapperUsername = new QueryWrapper<>();
      wrapperUsername.eq("username", username);
      Student tempUsername = studentService.getOne(wrapperUsername);
      if (tempUsername != null) {
        req.setAttribute("message", "账号已存在！");
        return "register";
      }
      if (!StringUtils.isEmpty(studentno)) {
        QueryWrapper<Student> wrapperStudentno = new QueryWrapper<>();
        wrapperStudentno.eq("studentno", studentno);
        Student tempStudentno = studentService.getOne(wrapperStudentno);
        if (tempStudentno != null) {
          req.setAttribute("message", "学号已存在！");
          return "register";
        }
      }
      Student student = new Student();
      student.setUsername(username);
      student.setPassword(password);
      student.setStudentno(studentno);
      student.setName(name);
      student.setId(CommonUtils.newId());
      student.setCreatetime(new Date());
      studentService.save(student);
      req.setAttribute("message", "注册成功，请登陆");
      return "login";
    }
    // 注册失败
    req.setAttribute("message", "请选择角色类型");
    return "register";
  }

  // 登出
  @RequestMapping("logout")
  public String logout() {
    // 让session失效。
    req.getSession().removeAttribute("user");
    return "login";
  }

  // 测试图表
  @RequestMapping("test-chart")
  public String testChart() {
    return "test-chart";
  }

  // 简单图表测试
  @RequestMapping("simple-chart")
  public String simpleChart() {
    return "simple-chart";
  }

  // 独立图表测试
  @RequestMapping("standalone-chart")
  public String standaloneChart() {
    return "standalone-chart";
  }

  // ECharts测试
  @RequestMapping("echarts-test")
  public String echartsTest() {
    return "echarts-test";
  }

}
