package com.example.newbeemall.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.service.TbNewbeeMallOrderItemService;
import com.example.newbeemall.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
public class AdminOrderItemController {

    @Resource
    private TbNewbeeMallOrderItemService tbNewbeeMallOrderItemService;


    /**
     * 查看订单
     */
    @GetMapping("/order-items/{orderId}")
    @ResponseBody
    public Object order_items(@PathVariable("orderId")Long orderId){
        System.out.println("idd========"+orderId);
        QueryWrapper<TbNewbeeMallOrderItem> wrapper = new QueryWrapper<TbNewbeeMallOrderItem>();
        wrapper.eq("order_id",orderId);
        List<TbNewbeeMallOrderItem> byId = tbNewbeeMallOrderItemService.list(wrapper);
        ResultUtil resultUtil = new ResultUtil(byId);
        return  resultUtil;
    }

    @RequestMapping("/orderitems/{orderId}")
    @ResponseBody
    public Object items(@PathVariable("orderId") Integer orderId){
        Map<String, Object> map = new HashMap<String, Object>();
        ArrayList<TbNewbeeMallOrderItem> tbNewbeeMallOrderItems = new ArrayList<>();
        List<TbNewbeeMallOrderItem> orderIdss = tbNewbeeMallOrderItemService.tbListItems(orderId);
        if(orderIdss != null){
            map.put("resultCode",200);
            map.put("data",orderIdss);
        }
        return  map;
    }
}

