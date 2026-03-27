package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.component.ApiResult;
import com.codeying.component.PagerFooterVO;
import com.codeying.entity.Admin;
import com.codeying.service.AdminService;
import com.codeying.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
/** 管理员控制器 关于管理员的增删改查操作都在这 */
@Controller
@RequestMapping("admin")
public class AdminController extends BaseController {

  @Autowired
  protected AdminService adminService;

  // 管理员列表页
  @RequestMapping("list")
  public String list(Model model, Integer pageIndex, Integer size, String username, String name) {
    if (pageIndex == null) {
      pageIndex = 1; // 默认访问第一页（分页）
    }
    if (size == null) {
      size = 15; // 默认每页15条
    }
    //查询条件
    QueryWrapper<Admin> paramMap = new QueryWrapper<>();
    paramMap.like(!StringUtils.isEmpty(username),"username", username);
    paramMap.like(!StringUtils.isEmpty(name),"name", name);
    paramMap.orderByAsc("id");
    // 开始分页
    IPage<Admin> pageInfo = new Page<Admin>().setCurrent(pageIndex).setSize(size);
    pageInfo = adminService.page(pageInfo, paramMap);
    //返回前端
    model.addAttribute("adminList", pageInfo.getRecords());
    model.addAttribute("pager",  new PagerFooterVO(pageInfo));
    //查询条件回显
    model.addAttribute("username", username);
    model.addAttribute("name", name);
    return "pages/admin-list";
  }

  // 管理员新增编辑页
  @RequestMapping("edit")
  public String edit(String id, Model model) {
    if(StringUtils.isEmpty(id)){
      return "pages/admin-add";
    }
    // 根据id获取到记录
    Admin entity = adminService.getById(id);
    model.addAttribute("item", entity);
    return "pages/admin-edit";
  }

  // 管理员详情页
  @RequestMapping("detail")
  public String detail(String id, Model model) {
    Admin entity = adminService.getById(id);
    model.addAttribute("item", entity);
    return "pages/admin-detail";
  }

  // 管理员保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult<Object> save(Admin entityTemp) {
    String id = entityTemp.getId(); //主键
    if (id == null || id.isEmpty()) { // 新增
      entityTemp.setId(CommonUtils.newId());
      entityTemp.setCreatetime(new Date());
      // 唯一校验
      QueryWrapper<Admin> wrapperusername = new QueryWrapper<>();
      wrapperusername.eq("username", entityTemp.getUsername());
      if (!adminService.list(wrapperusername).isEmpty()) {
        return fail("用户名 已存在！");
      }
      adminService.save(entityTemp);
    } else {
      adminService.updateById(entityTemp);
      if (getCurrentUser().getId().equals(id)) {
        setSessionValue("user", adminService.getById(id));
      }
    }
    return ApiResult.successMsg("保存成功"); // 返回保存成功
  }

  // 管理员删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult<Object> delete(String id) {
    // 根据ID删除记录
    boolean res = adminService.removeById(id);
    return res ? success() : fail();
  }

}

