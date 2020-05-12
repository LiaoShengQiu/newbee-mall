package com.example.newbeemall.controller.mall.workMq;

import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.example.newbeemall.entity.tb_seckill_order;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.service.TbSeckillOrderService;
import com.example.newbeemall.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: windy
 * @create: 2019-08-03 10:02
 */
@Component
public class WorkCustomer {

    private Logger logger= LoggerFactory.getLogger(WorkCustomer.class);
    @Resource
    private TbSeckillOrderService tb_seckill_orderService;
    @Resource
    private TbNewbeeMallShoppingCartItemService tbNewbeeMallShoppingCartItemService;
    @RabbitListener(queues="workMq")
    public void processA(Result msg) {
        System.out.println(msg.getId()+"ReceiveAjinglaile进来了====================="+msg+"\tqqqqq"+msg.getMessage());
        logger.info("ReceiveA:"+msg.toString());
        tb_seckill_order order = (tb_seckill_order) msg.getObj();
        TbNewbeeMallShoppingCartItem cartItem =  (TbNewbeeMallShoppingCartItem) msg.getGwc();
      //添加订单并加入购物车
       // tb_seckill_orderService.saveTbSeckillOrder(msg);
       boolean b = tb_seckill_orderService.save(order);
       boolean isItem = tbNewbeeMallShoppingCartItemService.save(cartItem);
        System.out.println(isItem+"添加订单结果"+b);
    }

}
