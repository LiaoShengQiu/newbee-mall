package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallOrderService extends IService<TbNewbeeMallOrder> {

    List<TbNewbeeMallOrder> findOrderByOrderId(Long userId);

    Long saveOrder(TbNewbeeMallUser user, List<TbNewbeeMallOrderItem> orderItem);

}
