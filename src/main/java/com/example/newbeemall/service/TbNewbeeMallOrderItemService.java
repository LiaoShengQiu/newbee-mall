package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
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
public interface TbNewbeeMallOrderItemService extends IService<TbNewbeeMallOrderItem> {

    List<TbNewbeeMallOrderItem> getOrderItemByOrderId(Long orderId);
}
