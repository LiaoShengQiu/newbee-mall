package com.example.newbeemall.controller;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import org.springframework.util.CollectionUtils;

import com.example.newbeemall.entity.TbNewbeeMallAdminUser;
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

@Controller
@RequestMapping("/admin")
public class TbNewbeeMallGoodsCategoryController {


    @Resource
    private TbNewbeeMallGoodsCategoryService GoodsCategoryService;
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

