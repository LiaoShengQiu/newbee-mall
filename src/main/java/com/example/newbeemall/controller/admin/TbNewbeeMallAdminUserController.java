package com.example.newbeemall.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallAdminUser;
import com.example.newbeemall.entity.TbNewbeeMallCarousel;
import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.mapper.TbNewbeeMallAdminUserMapper;
import com.example.newbeemall.mapper.TbNewbeeMallCarouselMapper;
import com.example.newbeemall.service.TbNewbeeMallAdminUserService;
import com.example.newbeemall.service.TbNewbeeMallCarouselService;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.utils.Md5;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    private TbNewbeeMallAdminUserService adminUserService;
    @Resource
    private TbNewbeeMallCarouselService carouselService;


    /**
     * 后台登录
     * @param userName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String login(String userName, String password, HttpSession session){
        if(userName==null || password == null){
            return "admin/login";
        }
        TbNewbeeMallAdminUser adminUser = adminUserService.login(userName,password);
        if(adminUser==null) {
            session.setAttribute("","用户名 或密码错误");
            return "admin/login";
        }
        session.setAttribute("admin",adminUser);
        return "admin/index";
    }

}

