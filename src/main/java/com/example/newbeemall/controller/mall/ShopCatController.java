package com.example.newbeemall.controller.mall;

import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShopCatController {
    @Resource
    private TbNewbeeMallShoppingCartItemService shopCatService;
    @GetMapping("/shop-cart")
    public Object toCart(HttpServletRequest request,HttpSession session) {
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) session.getAttribute("newBeeMallUser");
        int itemsTotal = 0;
        int priceTotal = 0;
        List<TbNewbeeMallOrderItem> cartByUserId = shopCatService.getCartByUserId(newBeeMallUser.getUserId());
        for (TbNewbeeMallOrderItem item:cartByUserId) {
            itemsTotal+=item.getGoodsCount();
            priceTotal+=item.getGoodsCount()*item.getSellingPrice();
        }
        request.setAttribute("itemsTotal",itemsTotal);
        request.setAttribute("priceTotal",priceTotal);
        request.setAttribute("myShoppingCartItems", cartByUserId);
        return "mall/cart";
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Object addShopCart(@RequestBody TbNewbeeMallShoppingCartItem cartItem,HttpServletRequest request,HttpSession session){
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) session.getAttribute("newBeeMallUser");
        cartItem.setUserId(newBeeMallUser.getUserId());
        ResultUtil resultUtil = new ResultUtil(shopCatService.saveCart(cartItem));
        int count = shopCatService.getCartCountByUserId(newBeeMallUser.getUserId());
        newBeeMallUser.setShopCartItemCount(count);
        session.setAttribute("newBeeMallUser",newBeeMallUser);
        return resultUtil;

    }
}
