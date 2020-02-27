package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.mapper.TbNewbeeMallOrderItemMapper;
import com.example.newbeemall.mapper.TbNewbeeMallOrderMapper;
import com.example.newbeemall.mapper.TbNewbeeMallShoppingCartItemMapper;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.utils.NumberUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
public class TbNewbeeMallOrderServiceImpl extends ServiceImpl<TbNewbeeMallOrderMapper, TbNewbeeMallOrder> implements TbNewbeeMallOrderService {

    @Resource
    private TbNewbeeMallOrderMapper orderMapper;
    @Resource
    private TbNewbeeMallOrderItemMapper itemMapper;
    @Resource
    private TbNewbeeMallShoppingCartItemMapper cartItemMapper;
    @Override
    public List<TbNewbeeMallOrder> findOrderByOrderId(Long userId) {
        return orderMapper.findOrderByOrderId(userId);
    }

    @Override
    public Long saveOrder(TbNewbeeMallUser user, List<TbNewbeeMallOrderItem> orders) {
        Long orderId = 0L;
        try{
            //先生成订单
            String orderNo = NumberUtil.genOrderNo();
            TbNewbeeMallOrder order = new TbNewbeeMallOrder();
            order.setOrderNo(orderNo);
            order.setUserId(user.getUserId());
            order.setUserAddress(user.getAddress());
            int priceTotal = 0;
            for (TbNewbeeMallOrderItem item:orders) {
                priceTotal+=item.getGoodsCount()*item.getSellingPrice();
            }
            order.setTotalPrice(priceTotal);
            //订单描述信息
            order.setExtraInfo("");
            //返回大于0代表插入成功
            if(orderMapper.insertSelective(order) > 0){
                //删除购物车商品
                UpdateWrapper<TbNewbeeMallShoppingCartItem> wrapper = new UpdateWrapper<TbNewbeeMallShoppingCartItem>();
                wrapper.eq("user_id",user.getUserId());
                cartItemMapper.delete(wrapper);
                //添加订单详情项
                for (TbNewbeeMallOrderItem item:orders) {
                    item.setOrderId(order.getOrderId());
                    itemMapper.insert(item);
                }
            }
            orderId=order.getOrderId();
        } catch (Exception e){
        }
        return orderId;
    }
}
