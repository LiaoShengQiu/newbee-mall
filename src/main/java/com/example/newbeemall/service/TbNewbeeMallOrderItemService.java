package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallOrderItemService extends IService<TbNewbeeMallOrderItem> {

    List<TbNewbeeMallOrderItem> getOrderItemByOrderId(Long orderId);

    public List<TbNewbeeMallOrderItem> tbListItems(Integer oid);
    public List<TbNewbeeMallOrderItem> findByUidList(Long userId);
}
