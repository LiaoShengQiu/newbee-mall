package com.example.newbeemall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallAdminUser;
import com.example.newbeemall.mapper.TbNewbeeMallAdminUserMapper;
import com.example.newbeemall.utils.Md5;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Controller
@RequestMapping("/admin")
public class TbNewbeeMallAdminUserController {

    @Resource
    private TbNewbeeMallAdminUserMapper adminUserMapper;

    @RequestMapping("/login")
    public String login(String userName, String password, HttpSession session){
        if(userName==null || password == null){
            return "admin/login";
        }
        QueryWrapper<TbNewbeeMallAdminUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_user_name",userName).eq("login_password", Md5.getMd5(password));
        TbNewbeeMallAdminUser adminUser = adminUserMapper.selectOne(wrapper);
        System.out.println(adminUser);
        if(adminUser==null) {
            session.setAttribute("","用户名 或密码错误");
            return "admin/login";
        }
        session.setAttribute("admin",adminUser);
        return "admin/index";
    }
}

