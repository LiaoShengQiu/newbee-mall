package com.example.newbeemall.controller.admin;


import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.mapper.TbNewbeeMallOrderMapper;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
 * 订单管理
 */
@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    @Resource
    private TbNewbeeMallOrderMapper tbNewbeeMallOrderMapper;
    @Resource
    private TbNewbeeMallOrderService tbNewbeeMallOrderService;
    @RequestMapping("/orders")
    public String tbNewbeeMallObder(){
        return "admin/newbee_mall_order";
    }

    @RequestMapping("/orders/list")
    @ResponseBody
    public Object orderList(@RequestParam Map<String,Object> map){
        int page = Integer.parseInt(map.get("page").toString());
        int limit = Integer.parseInt(map.get("limit").toString());
        map.put("start",(page-1)*limit);
        //查询条件page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
        List<TbNewbeeMallOrder> tbNewbeeMallOrders = tbNewbeeMallOrderMapper.order_list(map);
        return tbNewbeeMallOrders;
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
      boolean b = tbNewbeeMallOrderService.saveOrUpdate(tbNewbeeMallOrder);
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
                isok =   tbNewbeeMallOrderService.saveOrUpdate(tbNewbeeMallOrder);  //2 为配货完成
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
                isok =   tbNewbeeMallOrderService.saveOrUpdate(tbNewbeeMallOrder);
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
                isok =   tbNewbeeMallOrderService.saveOrUpdate(tbNewbeeMallOrder);
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
}

