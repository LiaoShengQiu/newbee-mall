package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.entity.TbSeckillGoods;
import com.example.newbeemall.entity.tb_seckill_order;
import com.example.newbeemall.mapper.TbSeckillOrderMapper;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.service.TbSeckillOrderService;
import com.example.newbeemall.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class TbSeckillOrderServiceImpl   extends ServiceImpl<TbSeckillOrderMapper, tb_seckill_order> implements TbSeckillOrderService {
  @Resource
  private TbSeckillOrderMapper tbSeckillOrderMapper;
  @Resource
  private TbNewbeeMallShoppingCartItemService tbNewbeeMallShoppingCartItemService;
    @Override
    public int deleteSeckill(long userId, long goodsId) {
        return tbSeckillOrderMapper.deleteSeckill(userId, goodsId);
    }

  /**
   * 秒杀成功后添加订单   并加入购物车
   * @param
   * @return
   */
  @Override
  public int saveTbSeckillOrder(Result result,HttpServletRequest request) {

    TbNewbeeMallShoppingCartItem cartItem2 = new TbNewbeeMallShoppingCartItem();
    addShopCart2(cartItem2, result.getId(), 1, request);
    return tbSeckillOrderMapper.insert((tb_seckill_order) result.getObj());
  }

  public void shuaxin(TbNewbeeMallShoppingCartItem cartItem,long goodsId,int goodsCount, HttpServletRequest request){
    System.out.println("刷新购物车===========");
    boolean o = addShopCart2(cartItem, goodsId, 1, request);
  }
  /**
   * 秒杀那边过来的  加入购物车
   * @param cartItem
   * @param
   * @return
   */
  public boolean addShopCart2(TbNewbeeMallShoppingCartItem cartItem,long goodsId,int goodsCount, HttpServletRequest request) {

    TbSeckillGoods tbSeckillGoods = (TbSeckillGoods) request.getSession().getAttribute("tbSeckillGoods");
    long guoqi = 2*60*60*1000;    //两个小时自动过期
    long shicha = 8*60*60*1000;  //8个小时的时差
    long endTime = tbSeckillGoods.getEndTime().getTime();  //获取时间戳
    System.out.println(tbSeckillGoods.getEndTime()+"活动结束时间"+endTime+"\taaaa"+tbSeckillGoods.getEndTime());
    Date now = new Date();
    System.out.println(now.getTime()+"当前时间"+now);
    //       Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    long time = now.getTime()+guoqi+shicha;  //过期时间的时间戳
    long to = 0;
    if(endTime - now.getTime() > 0){
      to = endTime - time;
    }
    System.out.println(to+"/shop-cart"+endTime);
    int isDeleted = 0;   //0显示 1删除
       /* if (to <= 0){
            isDeleted = 1;  //删除  过期
        }*/

    //结束时间
    Date time2 = new Date(time);
    //      LocalDateTime time2 =LocalDateTime.ofEpochSecond(time/1000,0,ZoneOffset.ofHours(8));
    System.out.println("结束时间======="+time2);
    TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) request.getSession().getAttribute("newBeeMallUser");
    cartItem.setUserId(newBeeMallUser.getUserId());
    cartItem.setIsDeleted(isDeleted);
    cartItem.setGoodsId(goodsId);
    cartItem.setGoodsCount(goodsCount);
    cartItem.setUpdateTime(time2);
    cartItem.setIsMiaos(1);  // 1表示是秒杀
    boolean isok =false;
    try{
      isok = tbNewbeeMallShoppingCartItemService.save(cartItem);
    }catch (Exception e){
      e.printStackTrace();
    }

    int count = tbNewbeeMallShoppingCartItemService.getCartCountByUserId(newBeeMallUser.getUserId());
    newBeeMallUser.setShopCartItemCount(count);
    request.getSession().setAttribute("newBeeMallUser",newBeeMallUser);
//        ResultUtil resultUtil = new ResultUtil(tbNewbeeMallShoppingCartItemService.saveCart(cartItem));
    return isok;
  }
}
