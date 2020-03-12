package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;

import java.util.List;
import java.util.Map;

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
    public List<TbNewbeeMallOrderItem> getCartByUserId2(Long userId);
    public boolean update(Map<String, Object> map);
}
