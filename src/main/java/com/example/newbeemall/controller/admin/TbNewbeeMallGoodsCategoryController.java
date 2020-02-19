package com.example.newbeemall.controller.admin;


import com.example.newbeemall.entity.TbNewbeeMallAdminUser;
import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    private TbNewbeeMallGoodsCategoryService goodsCategoryService;

    @RequestMapping("/index01")
    public String sanjiDaohan(HttpServletRequest request){
        //查询一级
        List<TbNewbeeMallGoodsCategory> yiji = goodsCategoryService.findYiji();
        List<TbNewbeeMallGoodsCategory> erji = null;
        List<TbNewbeeMallGoodsCategory> sanji = null;
        List<Long> erjis = new ArrayList<Long>();
        List<Long> sanjis = new ArrayList<Long>();
        //查询二、三级
        for (TbNewbeeMallGoodsCategory yi : yiji){
            System.out.println("二级的parentId"+yi.getCategoryId());
            erjis.add(yi.getCategoryId());
        }
        erji = goodsCategoryService.finderji(erjis);
        for (TbNewbeeMallGoodsCategory er : erji){
            System.out.println("san级的parentId"+er.getCategoryId());
            sanjis.add(er.getCategoryId());
        }
        sanji = goodsCategoryService.finderji(sanjis);
        request.setAttribute("yiji",yiji);
        request.setAttribute("erji",erji);
        request.setAttribute("sanji",sanji);
        return "/mall/index";
    }

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

