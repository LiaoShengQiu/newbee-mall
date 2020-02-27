package com.example.newbeemall.service.impl;

import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.mapper.TbNewbeeMallOrderItemMapper;
import com.example.newbeemall.service.TbNewbeeMallOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class TbNewbeeMallOrderItemServiceImpl extends ServiceImpl<TbNewbeeMallOrderItemMapper, TbNewbeeMallOrderItem> implements TbNewbeeMallOrderItemService {

    @Resource
    private TbNewbeeMallOrderItemMapper itemMapper;
    @Override
    @Transactional
    public List<TbNewbeeMallOrderItem> getOrderItemByOrderId(Long orderId) {
        return itemMapper.getOrderItemByOrderId(orderId);
    }

    @Override
    public List<TbNewbeeMallOrderItem> tbListItems(Integer oid) {
        return itemMapper.tbListItems(oid);
    }

    @Override
    public List<TbNewbeeMallOrderItem> findByUidList(Long userId) {
        return itemMapper.findByUidList(userId);
    }
}
