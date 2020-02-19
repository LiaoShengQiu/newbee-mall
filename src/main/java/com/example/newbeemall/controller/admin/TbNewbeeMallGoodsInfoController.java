package com.example.newbeemall.controller.admin;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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
public class TbNewbeeMallGoodsInfoController {

    @RequestMapping("/goods/edit")
    public String toGoodsEdit(){
        return "admin/newbee_mall_goods_edit";
    }
}

