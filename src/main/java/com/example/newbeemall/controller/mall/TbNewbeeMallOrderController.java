package com.example.newbeemall.controller.mall;


import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.service.TbNewbeeMallOrderItemService;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Controller
//@RequestMapping("/tbNewbeeMallOrder")
public class TbNewbeeMallOrderController {
    @Resource
    private TbNewbeeMallOrderService orderService;
    @Resource
    private TbNewbeeMallShoppingCartItemService shopCatService;
    @Resource
    private TbNewbeeMallOrderItemService itemService;

    @RequestMapping("saveOrder")
    public Object toOrder(HttpSession session, HttpServletRequest request){
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) session.getAttribute("newBeeMallUser");
        List<TbNewbeeMallOrderItem> cartByUserId = shopCatService.getCartByUserId(newBeeMallUser.getUserId());
        Long orderId = orderService.saveOrder(newBeeMallUser,cartByUserId);
        if(orderId==0){

        }
        TbNewbeeMallOrder order = orderService.getById(orderId);
        order.setOrderItems(itemService.getOrderItemByOrderId(orderId));
        System.out.println(order.getCreateTime());
        request.setAttribute("orderDetail",order);
        return "mall/order-detail";
    }
}

