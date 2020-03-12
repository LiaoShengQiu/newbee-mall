package com.example.newbeemall.controller.mall;

import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GoodsController {

    @Resource
    private TbNewbeeMallGoodsInfoService goodsService;

    @RequestMapping("/goods/detail/{goodsId}")
    public String toDetail(@PathVariable("goodsId") Long goodsId, HttpServletRequest request){
        if(goodsId < 1){
            return "error/error_5xx";
        }
        TbNewbeeMallGoodsInfo goods = goodsService.getById(goodsId);
        request.setAttribute("goodsDetail",goods);
        return "mall/detail";
    }


}
