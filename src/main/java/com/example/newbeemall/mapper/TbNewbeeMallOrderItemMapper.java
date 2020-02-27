package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallOrderItemMapper extends BaseMapper<TbNewbeeMallOrderItem> {

    List<TbNewbeeMallOrderItem> getOrderItemByOrderId(@Param("orderId") Long orderId);
}
