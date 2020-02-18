package com.example.newbeemall.service.impl;

import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.example.newbeemall.mapper.TbNewbeeMallShoppingCartItemMapper;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TbNewbeeMallShoppingCartItemServiceImpl extends ServiceImpl<TbNewbeeMallShoppingCartItemMapper, TbNewbeeMallShoppingCartItem> implements TbNewbeeMallShoppingCartItemService {

    @Resource
    private TbNewbeeMallShoppingCartItemMapper mapper;
    @Override
    public boolean saveCart(TbNewbeeMallShoppingCartItem cartItem) {
        return mapper.saveCart(cartItem)==1;
    }

    @Override
    public int getCartCountByUserId(Long userId) {
        return mapper.getCartCountByUserId(userId);
    }

    @Override
    public List<TbNewbeeMallOrderItem> getCartByUserId(Long userId) {
        return mapper.getCartByUserId(userId);
    }
}
