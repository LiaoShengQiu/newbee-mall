package com.example.newbeemall.controller.mall;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.mapper.TbNewbeeMallOrderItemMapper;
import com.example.newbeemall.mapper.TbNewbeeMallOrderMapper;
import com.example.newbeemall.service.TbNewbeeMallOrderItemService;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.utils.NewBeeMallOrderStatusEnum;
import com.example.newbeemall.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
 * @since 2020-02-07
 */
@Controller
public class OrderController {
    @Resource
    private TbNewbeeMallOrderMapper tbNewbeeMallOrderMapper;
    @Resource
    private TbNewbeeMallOrderItemMapper tbNewbeeMallOrderItemMapper;
    @Resource
    private TbNewbeeMallOrderService orderService;
    @Resource
    private TbNewbeeMallShoppingCartItemService shopCatService;
    @Resource
    private TbNewbeeMallOrderItemService itemService;

    @GetMapping("/paySuccess")
    @ResponseBody
    public Object paySuccess(TbNewbeeMallOrder order,HttpServletRequest request){
        UpdateWrapper<TbNewbeeMallOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("order_status",1);
        updateWrapper.eq("order_no",order.getOrderNo());
        boolean b = orderService.update(updateWrapper);
        return new ResultUtil(b);
    }
    @RequestMapping("/payPage")
    public String payPage(TbNewbeeMallOrder order,HttpServletRequest request){
        boolean b = orderService.updatePayType(order);
        TbNewbeeMallOrder orderByOrderNo = orderService.findOrderByOrderNo(order.getOrderNo());
        request.setAttribute("orderNo",orderByOrderNo.getOrderNo());
        request.setAttribute("totalPrice",orderByOrderNo.getTotalPrice());
        if(order.getPayType()==1){
            return "mall/alipay";
        }
        return "mall/wxpay";
    }

    @RequestMapping("/selectPayType")
    public String toSelectPay(String c,HttpServletRequest request){
        TbNewbeeMallOrder orderByOrderNo = orderService.findOrderByOrderNo(c);
        request.setAttribute("orderNo",orderByOrderNo.getOrderNo());
        request.setAttribute("totalPrice",orderByOrderNo.getTotalPrice());
        return "mall/pay-select";
    }

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
        request.setAttribute("orderDetailVO",order);
        return "mall/order-detail";
    }

    /**
     * 修改订单
     */
    @RequestMapping("/orders/update")
    @ResponseBody
    public Object update(@RequestBody Map<String,Object> map){
        System.out.println("id===="+map.get("orderId")+"价格"+map.get("totalPrice")+map.get("userAddress"));
        TbNewbeeMallOrder tbNewbeeMallOrder = new TbNewbeeMallOrder();
        tbNewbeeMallOrder.setOrderId(Long.parseLong(map.get("orderId").toString()));
        tbNewbeeMallOrder.setTotalPrice(Integer.parseInt(map.get("totalPrice").toString()));
        tbNewbeeMallOrder.setUserAddress(map.get("userAddress").toString());
        boolean b = orderService.saveOrUpdate(tbNewbeeMallOrder);
        /*  int num = tbNewbeeMallOrderService.updataOrder(map);*/
        if (b){
            System.out.println("修改成功！");
            map.put("resultCode",200);
        }else{
            System.out.println("修改失败");
        }
        return  map;
    }

    /**
     * 配货
     */
    @RequestMapping("/orders/checkDone")
    @ResponseBody
    public Object peihuo(@RequestBody Map<String,Object> map,String iids){
        String str =  map.get("ids").toString().trim();
        String substr = str.substring(1, str.length() - 1).trim();
        System.out.println(substr.trim().length()+"================"+substr);
        String s1 = substr.replace(", ", ",");
        System.out.println(s1+"==============");
        String[] split = s1.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        TbNewbeeMallOrder tbNewbeeMallOrder = new TbNewbeeMallOrder();
        for (String s :split){
            System.out.println(Integer.parseInt(s));
            tbNewbeeMallOrder.setOrderStatus(2);     //2 为配货完成
            tbNewbeeMallOrder.setOrderId(Long.parseLong(s));
            boolean isok = false;
            try{
                isok =   orderService.saveOrUpdate(tbNewbeeMallOrder);  //2 为配货完成
                if (isok){
                    map.put("resultCode",200);
                    System.out.println("配货成功！");
                }
            }catch (Exception e){
                map.put("message","系统开小差了,配货失败！");
            }
        }

        return map;
    }

    /**
     * 出库
     */
    @RequestMapping("/orders/checkOut")
    @ResponseBody
    public Object cheOut(@RequestBody Map<String,Object> map){
        String str =  map.get("ids").toString().trim();
        String substr = str.substring(1, str.length() - 1).trim();
        System.out.println(substr.trim().length()+"================"+substr);
        String s1 = substr.replace(", ", ",");
        System.out.println(s1+"==============");
        String[] split = s1.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        TbNewbeeMallOrder tbNewbeeMallOrder = new TbNewbeeMallOrder();
        for (String s :split){
            System.out.println(Integer.parseInt(s));
            tbNewbeeMallOrder.setOrderStatus(3);     //2 为配货完成,3为出库
            tbNewbeeMallOrder.setOrderId(Long.parseLong(s));
            boolean isok = false;
            try{
                isok =   orderService.saveOrUpdate(tbNewbeeMallOrder);
                if (isok){
                    map.put("resultCode",200);
                    System.out.println("出库成功！");
                }
            }catch (Exception e){
                map.put("message","系统开小差了,出库失败！");
            }
        }
        return map;
    }
    /**
     * 关闭
     */
    @RequestMapping("/orders/close")
    @ResponseBody
    public Object close(@RequestBody Map<String,Object> map){
        String str =  map.get("ids").toString().trim();
        String substr = str.substring(1, str.length() - 1).trim();
        System.out.println(substr.trim().length()+"================"+substr);
        String s1 = substr.replace(", ", ",");
        System.out.println(s1+"==============");
        String[] split = s1.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        TbNewbeeMallOrder tbNewbeeMallOrder = new TbNewbeeMallOrder();
        for (String s :split){
            System.out.println(Integer.parseInt(s));
            tbNewbeeMallOrder.setOrderStatus(-3);     //2 为配货完成,3为出库，-3商家关闭
            tbNewbeeMallOrder.setOrderId(Long.parseLong(s));
            boolean isok = false;
            try{
                isok =   orderService.saveOrUpdate(tbNewbeeMallOrder);
                if (isok){
                    map.put("resultCode",200);
                    System.out.println("关闭成功！");
                }
            }catch (Exception e){
                map.put("message","系统开小差了,关闭失败！");
            }
        }
        return map;
    }

    /**
     * 订单详情
     */
    @RequestMapping("/orders/{orderNo}")
    public String ordersByNo(@PathVariable("orderNo") String orderNo,HttpServletRequest request){
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) request.getSession().getAttribute("newBeeMallUser");
        System.out.println("/orders/{orderNo}"+orderNo+"newBeeMallUserId"+newBeeMallUser.getUserId());
       QueryWrapper<TbNewbeeMallOrder> query = new QueryWrapper<>();
        query.eq("order_no",orderNo);
        /* query.eq("user_id",newBeeMallUser.getUserId());
        List<TbNewbeeMallOrder> list = orderService.list(query);
        request.setAttribute("orderDetailVO",list.get(0));*/
        //根据订单号查询
        TbNewbeeMallOrder tbNewbeeMallOrder = tbNewbeeMallOrderMapper.selectOne(query);
        QueryWrapper<TbNewbeeMallOrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",tbNewbeeMallOrder.getOrderId());
        List<TbNewbeeMallOrderItem> list = itemService.list(queryWrapper);
        tbNewbeeMallOrder.setOrderItems(list);

        /**
         * 0.无 1.支付宝支付 2.微信支付  -1 报错
         */
        String payStr = "-1";
        if(tbNewbeeMallOrder.getPayType() == 1){
            payStr = "支付宝支付";
        }else if(tbNewbeeMallOrder.getPayType() == 2){
            payStr = "微信支付";
        }else if(tbNewbeeMallOrder.getPayType() == 0){
            payStr = "无";
        }
        tbNewbeeMallOrder.setPayTypeString(payStr);
        tbNewbeeMallOrder.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(tbNewbeeMallOrder.getOrderStatus()).getName());
       request.setAttribute("orderDetailVO",tbNewbeeMallOrder);
        return "mall/order-detail";
    }
}

