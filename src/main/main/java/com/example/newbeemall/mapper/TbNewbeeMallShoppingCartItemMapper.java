package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallShoppingCartItemMapper extends BaseMapper<TbNewbeeMallShoppingCartItem> {

    /**
     * 添加购物车
     * @param cartItem
     * @return
     */
    public int saveCart(TbNewbeeMallShoppingCartItem cartItem);

    /**
     * 获取购物车的数量
     * @param userId
     * @return
     */
    public int getCartCountByUserId(@Param("userId") Long userId);

    /**
     * 获取购物车商品
     * @param userId
     * @return
     */
    public List<TbNewbeeMallOrderItem> getCartByUserId(@Param("userId") Long userId);

    public int updateCartCount(Map<String,Object> map);
}
