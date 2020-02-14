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
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@MapperScan("com.example.newbeemall.mapper")
@RequestMapping("/tbNewbeeMallAdminUser")
public class TbNewbeeMallAdminUserController {


    @Resource
    private TbNewbeeMallGoodsCategoryService newbeeMallGoodsCategoryService;
    @Resource
    private TbNewbeeMallCarouselService newbeeMallCarouselService;
    @Resource
    private TbNewbeeMallIndexConfigService newbeeMallIndexConfigService;


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
        request.setAttribute("categories", categories);//分类数据
        request.setAttribute("carousels", carousels);//轮播图
        request.setAttribute("hotGoodses", hotGoodses);//热销商品
        request.setAttribute("newGoodses", newGoodses);//新品
        request.setAttribute("recommendGoodses", recommendGoodses);//推荐商品
        return "mall/index";
    }
}

