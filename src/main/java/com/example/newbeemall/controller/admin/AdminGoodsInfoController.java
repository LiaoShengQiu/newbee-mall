package com.example.newbeemall.controller.admin;


import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsInfoMapper;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import com.example.newbeemall.utils.ResultGenerator;
import com.example.newbeemall.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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
@RequestMapping("/admin")
public class AdminGoodsInfoController {

    @Resource
    private TbNewbeeMallGoodsInfoService goodsInfoService;
    @Resource
    private TbNewbeeMallGoodsCategoryService goodsCategoryService;
    @Resource
    private TbNewbeeMallGoodsInfoMapper tbNewbeeMallGoodsInfoMapper;

    @RequestMapping("/goods")
    public String goodsInfo(){
        return "admin/newbee_mall_goods.html";
    }

    @RequestMapping("/goods/edit")
    public String toGoodsEdit(HttpServletRequest request){
        //商品分类-一级分类
        List<TbNewbeeMallGoodsCategory> firstLevelCate = goodsCategoryService.getFirstLevelCate();
        //第一个一级分类下面的二级分类
        List<TbNewbeeMallGoodsCategory> secondLevelCategories = goodsCategoryService.getlistForSelect(firstLevelCate.get(0).getCategoryId());
        //第一个二级分类下的三级分类
        List<TbNewbeeMallGoodsCategory> thirdLevelCategories = goodsCategoryService.getlistForSelect(secondLevelCategories.get(0).getCategoryId());
        request.setAttribute("firstLevelCategories",firstLevelCate);
        request.setAttribute("secondLevelCategories",secondLevelCategories);
        request.setAttribute("thirdLevelCategories",thirdLevelCategories);
        return "admin/newbee_mall_goods_edit";
    }

    //异步返回所有商品数据-分页查询
    @RequestMapping("/goods/list")
    @ResponseBody
    public Object goodsList(@RequestParam Map<String,Object> map, HttpSession session){
        System.out.println(map.toString()+"===========");
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer limit = Integer.parseInt(map.get("limit").toString());

       /* System.out.println(page+"\t aaa"+limit);
        Map mp = new HashMap();
        mp = goodsInfoService.getList(page,limit);
        System.out.println("============="+mp.size());
        List<TbNewbeeMallGoodsInfo> data = new ArrayList<TbNewbeeMallGoodsInfo>();
        data = (List<TbNewbeeMallGoodsInfo>) mp.get("list");
        map.put("totalPage",mp.get("totalPage"));
        map.put("currPage",mp.get("currPage"));
        map.put("totalCount",mp.get("totalCount"));
//                goodsInfoService.getList(page,limit);
        return data;*/

        map.put("start",(page-1)*limit);
        PageQueryUtil pageUtil = new PageQueryUtil(map);
        //查询条件page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
        PageResult data = goodsInfoService.searchsp2(pageUtil);
        System.out.println("=="+data.getTotalCount()+"总记录=======");
        return ResultGenerator.genSuccessResult(data);
    }
    //修改商品-查询商品信息到页面
    @RequestMapping("/goods/edit/{goodsId}")
    public String goodsEdit(@PathVariable("goodsId")Integer goodsId,HttpServletRequest request){
        //商品分类-一级分类
        List<TbNewbeeMallGoodsCategory> firstLevelCate = goodsCategoryService.getFirstLevelCate();
        //要修改的商品
        TbNewbeeMallGoodsInfo info = goodsInfoService.getById(goodsId);
        //所属2级分类
        Long three = info.getGoodsCategoryId();
        //所属2级分类
        Long two = goodsCategoryService.findParentId(three);
        //所属1级分类
        Long one = goodsCategoryService.findParentId(two);
        //查询所属一级分类下的二级分类
        List<TbNewbeeMallGoodsCategory> secondLevelCategories = goodsCategoryService.getlistForSelect(one);
        //查询所属二级分类下的三级分类
        List<TbNewbeeMallGoodsCategory> thirdLevelCategories = goodsCategoryService.getlistForSelect(two);
        request.setAttribute("firstLevelCategoryId",one);
        request.setAttribute("secondLevelCategoryId",two);
        request.setAttribute("thirdLevelCategoryId",three);
        request.setAttribute("firstLevelCategories",firstLevelCate);
        request.setAttribute("secondLevelCategories",secondLevelCategories);
        request.setAttribute("thirdLevelCategories",thirdLevelCategories);
        request.setAttribute("goods",info);
        return "admin/newbee_mall_goods_edit.html";
    }

    @RequestMapping("/categories/listForSelect")
    @ResponseBody
    public Object listForSelect(@RequestParam("categoryId") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<TbNewbeeMallGoodsCategory> list = new ArrayList<TbNewbeeMallGoodsCategory>();
        list = goodsCategoryService.getlistForSelect(id);
        System.out.println(list.toString());
        Map<String,Object> map = new HashMap();

        Map data = new HashMap();
        if(goodsCategoryService.isLevelTwo(id)){
            data.put("secondLevelCategories",list);
        }else {
            data.put("thirdLevelCategories", list);
        }
        map.put("data",data);
        map.put("resultCode",200);

        request.setAttribute("secondLevelCategories",list);
        request.setAttribute("resultCode",200);

        return map;
    }

    @RequestMapping("goods/status/0")
    @ResponseBody
    public Object upGoods(@RequestBody Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map map = new HashMap();
        try{
            goodsInfoService.upGoods(ids);
            map.put("resultCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("goods/status/1")
    @ResponseBody
    public Object downGoods(@RequestBody Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("显示+++++++++" + ids[0].toString());
        Map map = new HashMap();
        try{
            goodsInfoService.downGoods(ids);
            map.put("resultCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("goods/update")
    @ResponseBody
    public Object update(@RequestParam TbNewbeeMallGoodsInfo entity){
        boolean save = goodsInfoService.updateById(entity);
        return new ResultUtil(save,save?"修改成功":"修改失败");
    }

    @RequestMapping("goods/save")
    @ResponseBody
    public Object save(@RequestBody TbNewbeeMallGoodsInfo entity
    ) throws IOException {
        boolean save = goodsInfoService.save(entity);
        return new ResultUtil(save,save?"保存成功":"保存失败");
    }
}

