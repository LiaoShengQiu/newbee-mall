package com.example.newbeemall.controller.admin;

import com.example.newbeemall.service.*;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.Result;
import com.example.newbeemall.utils.ResultGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.example.newbeemall.entity.TbNewbeeMallAdminUser;


import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Resource
    private TbNewbeeMallAdminUserService adminUserService;

    @Resource
    private TbNewbeeMallUserService userService;


    /**
     * 后台登录
     */
    @RequestMapping("/login")
    public String login(String userName, String password,String verifyCode, HttpSession session){
        System.out.println("login...........................................................");
        Object tu = session.getAttribute("text");
        if(verifyCode==null || verifyCode==""){
            return "admin/login";
        }
        if(tu.toString().equalsIgnoreCase(verifyCode)){
            if(userName==null || password == null){
                return "admin/login";
            }
            TbNewbeeMallAdminUser adminUser = adminUserService.login(userName,password);
            if(adminUser == null) {
                session.setAttribute("errorMsg","用户名 或密码错误");
                return "admin/login";
            }
            session.setAttribute("admin",adminUser);
            return "admin/index";
        } else{
            session.setAttribute("errorMsg","验证码错误");
            return "admin/login";
        }

    }

    /**
     * 会员管理
     */
    @GetMapping("/users")
    public String users(HttpServletRequest request) {
        System.out.println("会员管理*******************************");
        request.setAttribute("path", "users");
        return "admin/newbee_mall_user";
    }



    /**
     * 列表
     */
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(userService.getNewBeeMallUsersPage(pageUtil));
    }


    /**
     * 用户禁用与解除禁用
     */
    @RequestMapping(value = "/users/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable int lockStatus) {
        System.out.println("用户禁用与解除禁用...........................................");
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return ResultGenerator.genFailResult("操作非法！");
        }
        if (userService.lockUsers(ids, lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("禁用失败");
        }
    }
}

