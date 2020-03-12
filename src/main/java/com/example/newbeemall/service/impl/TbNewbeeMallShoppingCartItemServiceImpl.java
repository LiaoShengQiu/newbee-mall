package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.example.newbeemall.mapper.TbNewbeeMallShoppingCartItemMapper;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
@Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
public class TbNewbeeMallShoppingCartItemServiceImpl extends ServiceImpl<TbNewbeeMallShoppingCartItemMapper, TbNewbeeMallShoppingCartItem> implements TbNewbeeMallShoppingCartItemService {

    @Resource
    private TbNewbeeMallShoppingCartItemMapper mapper;
    @Override
    @Transactional()
    public boolean saveCart(TbNewbeeMallShoppingCartItem cartItem) {
        boolean isok = mapper.saveCart(cartItem)==1;
        return isok;
    }

    @Override
    public int getCartCountByUserId(Long userId) {
        return mapper.getCartCountByUserId(userId);
    }

    @Override
    public List<TbNewbeeMallOrderItem> getCartByUserId(Long userId) {
        return mapper.getCartByUserId(userId);
    }

    @Override
    public List<TbNewbeeMallOrderItem> getCartByUserId2(Long userId) {
        return mapper.getCartByUserId2(userId);
    }

    @Override
    public boolean update(Map<String,Object> map) {
        return mapper.updateCartCount(map)>0;
    }
}
