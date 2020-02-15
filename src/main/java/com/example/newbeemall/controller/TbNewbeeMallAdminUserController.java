package com.example.newbeemall.controller;

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

@RequestMapping("/admin")
public class TbNewbeeMallAdminUserController {

    @Resource
    private TbNewbeeMallAdminUserService adminUserService;
    @Resource
    private TbNewbeeMallCarouselService carouselService;

    @Resource
    private TbNewbeeMallGoodsCategoryService newbeeMallGoodsCategoryService;
    @Resource
    private TbNewbeeMallCarouselService newbeeMallCarouselService;
    @Resource
    private TbNewbeeMallIndexConfigService newbeeMallIndexConfigService;


    /**
     * 后台登录
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


    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        System.out.println("首页........................................");
        List<NewBeeMallIndexCategoryVO> categories = newbeeMallGoodsCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            return "error/error_5xx";
        }
        List<NewBeeMallIndexCarouselVO> carousels = newbeeMallCarouselService.getCarouselsForIndex(5);
        List<NewBeeMallIndexConfigGoodsVO> hotGoodses = newbeeMallIndexConfigService.getConfigGoodsesForIndex(3, 4);
        List<NewBeeMallIndexConfigGoodsVO> newGoodses = newbeeMallIndexConfigService.getConfigGoodsesForIndex(4, 5);
        List<NewBeeMallIndexConfigGoodsVO> recommendGoodses = newbeeMallIndexConfigService.getConfigGoodsesForIndex(5, 1);
        System.out.println("recommendGoodses==============>"+recommendGoodses);
        request.setAttribute("categories", categories);
        request.setAttribute("carousels", carousels);
        request.setAttribute("hotGoodses", hotGoodses);
        request.setAttribute("newGoodses", newGoodses);
        request.setAttribute("recommendGoodses", recommendGoodses);
        return "mall/index";
    }
}

