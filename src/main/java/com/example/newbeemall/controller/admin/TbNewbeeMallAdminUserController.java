package com.example.newbeemall.controller.admin;

import com.example.newbeemall.service.TbNewbeeMallCarouselService;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.service.TbNewbeeMallIndexConfigService;
import com.example.newbeemall.vo.NewBeeMallIndexCarouselVO;
import com.example.newbeemall.vo.NewBeeMallIndexCategoryVO;
import com.example.newbeemall.vo.NewBeeMallIndexConfigGoodsVO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TbNewbeeMallAdminUserController {

    @Resource
    private TbNewbeeMallAdminUserService adminUserService;


    /**
     * 后台登录
     */
    @RequestMapping("/login")
    public String login(String userName, String password, HttpSession session){
        System.out.println("login...........................................................");
        if(userName==null || password == null){
            return "admin/login";
        }
        TbNewbeeMallAdminUser adminUser = adminUserService.login(userName,password);
        if(adminUser == null) {
            session.setAttribute("","用户名 或密码错误");
            return "admin/login";
        }
        session.setAttribute("admin",adminUser);
        return "admin/index";
    }
}

