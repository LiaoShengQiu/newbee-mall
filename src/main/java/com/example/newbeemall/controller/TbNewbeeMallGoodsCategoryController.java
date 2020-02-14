package com.example.newbeemall.controller;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Controller
@RequestMapping("/tbNewbeeMallGoodsCategory")
public class TbNewbeeMallGoodsCategoryController {


    @Resource
    private TbNewbeeMallGoodsCategoryService GoodsCategoryService;


    @RequestMapping("/categories/listForSelect")
    @ResponseBody
    public Map<String, String> listForSelect(@RequestParam("categoryId") Long categoryId) {
        System.out.println("显示左侧菜单。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        /*if (categoryId == null || categoryId < 1) {
            return ResultGenerator.genFailResult("缺少参数！");
        }*/
        TbNewbeeMallGoodsCategory category = GoodsCategoryService.getGoodsCategoryById(categoryId);
        //既不是一级分类也不是二级分类则为不返回数据
        /*if (category == null || category.getCategoryLevel() == 3) {
            return ResultGenerator.genFailResult("参数异常！");
        }*/
        Map categoryResult = new HashMap(2);
        if (category.getCategoryLevel() == 1) {
            //如果是一级分类则返回当前一级分类下的所有二级分类，以及二级分类列表中第一条数据下的所有三级分类列表
            //查询一级分类列表中第一个实体的所有二级分类
            List<TbNewbeeMallGoodsCategory> secondLevelCategories = GoodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), 2);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<TbNewbeeMallGoodsCategory> thirdLevelCategories = GoodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), 3);
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCategoryLevel() == 2) {
            //如果是二级分类则返回当前分类下的所有三级分类列表
            List<TbNewbeeMallGoodsCategory> thirdLevelCategories = GoodsCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), 3);
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        return categoryResult;
    }
}

