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
import java.util.Map;

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

    @PutMapping("/shop-cart")
    @ResponseBody
    public Object updateShopCart(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session){
        ResultUtil resultUtil = new ResultUtil(shopCatService.update(map));
        return resultUtil;

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

    /**
     * 删除购物车商品
     * @param id
     * @param request
     * @param session
     * @return
     */
    @DeleteMapping("/shop-cart/{id}")
    @ResponseBody
    public Object deleteShopCart(@PathVariable("id") Long id,HttpServletRequest request,HttpSession session){
        return new ResultUtil(shopCatService.removeById(id));

    }

    @GetMapping("/shop-cart/settle")
    public Object settle(HttpServletRequest request,HttpSession session){
        return "mall/order-settle";

    }
}
