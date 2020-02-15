package com.example.newbeemall.controller;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import org.springframework.util.CollectionUtils;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.entity.TbNewbeeMallAdminUser;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.utils.ResultUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ***
 * @since 2020-02-07`
 */
@Controller
@RequestMapping("/admin")
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
    @Resource
    private TbNewbeeMallGoodsCategoryService goodsCategoryService;

    @RequestMapping("/categories/save")
    @ResponseBody
    public Object save(@RequestBody Map<String,Object> map,HttpSession session){
		TbNewbeeMallAdminUser user = (TbNewbeeMallAdminUser)session.getAttribute("admin");
        System.out.println(map.toString());
		map.put("createUser",user.getAdminUserId());
		boolean isok = goodsCategoryService.save(map);
	    ResultUtil resultObject = new ResultUtil(isok);
        return resultObject;
    }
	
	@RequestMapping("/categories/delete")
    @ResponseBody
    public Object delete(@RequestParam Map<String,Object> map){
		
        return null;
    }
	
	@RequestMapping("/categories/update")
    @ResponseBody
    public Object update(@RequestBody Map<String,Object> map,HttpSession session){
		TbNewbeeMallAdminUser user = (TbNewbeeMallAdminUser)session.getAttribute("admin");
		map.put("updateUser",user.getAdminUserId());
		boolean isok = goodsCategoryService.update(map);
	    ResultUtil resultObject = new ResultUtil(isok);
        return resultObject;
    }



    @RequestMapping("/categories/list")
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map){
        int page = Integer.parseInt(map.get("page").toString());
        int limit = Integer.parseInt(map.get("limit").toString());
        map.put("start",(page-1)*limit);
        //查询条件parentId=父级，categoryLevel=分类等级, page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
        List<TbNewbeeMallGoodsCategory> goodsCategorys = goodsCategoryService.findGoodsCategory(map);
        return goodsCategorys;
    }

    @RequestMapping("/categories")
    public String categories(int parentId, int categoryLevel, int backParentId, HttpServletRequest request){
        request.setAttribute("parentId",parentId);
        request.setAttribute("categoryLevel",categoryLevel);
        request.setAttribute("backParentId",backParentId);
        return "admin/newbee_mall_category";
    }
}

