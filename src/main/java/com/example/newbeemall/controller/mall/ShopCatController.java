package com.example.newbeemall.controller.mall;

import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class ShopCatController {
    @Resource
    private TbNewbeeMallShoppingCartItemService shopCatService;

    @RequestMapping("/shop-cart")
    public Object addShopCart(@RequestBody TbNewbeeMallShoppingCartItem cartItem, HttpSession session){
        session.getAttribute("newBeeMallUser");
        shopCatService.save(cartItem);
        return new ResultUtil();

    }
}
