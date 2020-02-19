package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallShoppingCartItemService extends IService<TbNewbeeMallShoppingCartItem> {

    public boolean saveCart(TbNewbeeMallShoppingCartItem cartItem);
    /**
     * 获取购物车的数量
     * @param userId
     * @return
     */
    public int getCartCountByUserId(Long userId);
    /**
     * 获取购物车商品
     * @param userId
     * @return
     */
    public List<TbNewbeeMallOrderItem> getCartByUserId(Long userId);
}
