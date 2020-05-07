package com.example.newbeemall.controller.admin;


import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import com.example.newbeemall.utils.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
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
    private TbNewbeeMallOrderService tbNewbeeMallOrderService;
    @RequestMapping("/orders")
    public String tbNewbeeMallObder(){
        return "admin/newbee_mall_order";
    }

    @RequestMapping("/orders/list")
    @ResponseBody
    public Object orderList(@RequestParam Map<String,Object> map){
        /*if(map.get("sidx")!=null){
            map.put("sidx", NewBeeMallUtils.zh(map.get("sidx").toString()));
        }*/
        //查询条件page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
        PageQueryUtil pageQueryUtil = new PageQueryUtil(map);
        PageResult pageResult = tbNewbeeMallOrderService.order_list(pageQueryUtil);
        return ResultGenerator.genSuccessResult(pageResult);
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
    public Object peihuo(@RequestBody Integer[] ids){

        int num = 0;
        HashMap<String, Object> map = new HashMap<>();
        try{
           num = tbNewbeeMallOrderService.updateStatus(ids, 2);
            map.put("resultCode",200);
                    System.out.println("配货成功！");
        }catch (Exception e){
            map.put("resultCode",500);
            map.put("message","系统开小差了,配货失败！");
        }


        return map;
    }

    /**
     * 出库
     */
    @RequestMapping("/orders/checkOut")
    @ResponseBody
    public Object cheOut(@RequestBody Integer[] ids){
        int num = 0;
        HashMap<String, Object> map = new HashMap<>();
        try{
            num = tbNewbeeMallOrderService.updateStatus(ids, 3);
            map.put("resultCode",200);
            System.out.println("出库成功！");
        }catch (Exception e){
            map.put("resultCode",500);
            map.put("message","系统开小差了,出库失败！");
        }
        return map;
    }
    /**
     * 关闭
     */
    @RequestMapping("/orders/close")
    @ResponseBody
    public Object close(@RequestBody Integer[] ids){
        int num = 0;
        HashMap<String, Object> map = new HashMap<>();
        try{
            num = tbNewbeeMallOrderService.updateStatus(ids, -3);
            map.put("resultCode",200);
            System.out.println("关闭成功！");
        }catch (Exception e){
            map.put("resultCode",500);
            map.put("message","系统开小差了,关闭失败！");
        }
        return map;
    }
}

