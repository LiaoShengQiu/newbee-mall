package com.example.newbeemall.controller.mall;

import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.vo.SearchPageCategoryVO;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GoodsCategoryController {


    @Resource
    private TbNewbeeMallGoodsCategoryService goodsCategoryService;
    @Resource
    private TbNewbeeMallGoodsInfoService goodsInfoService;


    @RequestMapping("/search")
    public String search(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        System.out.println("商品详情****************************************");
        if (StringUtils.isEmpty(map.get("page"))) {
            map.put("page", 1);
        }
        map.put("limit", 10);
        if (map.containsKey("goodsCategoryId") && !StringUtils.isEmpty(map.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(map.get("goodsCategoryId") + "");
            SearchPageCategoryVO pcVO = goodsCategoryService.getCategoryById(categoryId);
            if (pcVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("pcVO", pcVO);
            }
        }
        String keyword = "";
        if (map.containsKey("keyword") && !StringUtils.isEmpty((map.get("keyword") + "").trim())) {
            keyword = map.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        map.put("keyword", keyword);
        PageQueryUtil pageUtil = new PageQueryUtil(map);
        request.setAttribute("pageResult", goodsInfoService.searchsp(pageUtil));
        return "mall/search";
    }

}

