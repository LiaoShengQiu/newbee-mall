package com.example.newbeemall.controller.admin;

import com.example.newbeemall.entity.TbNewbeeMallAdminUser;
import com.example.newbeemall.service.TbNewbeeMallAdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class TbNewbeeMallAdminUserController {

    @Resource
    private TbNewbeeMallAdminUserService adminUserService;


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
            return "admin/index";
        }

    }
}

