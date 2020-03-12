package com.example.newbeemall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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


    @Select("SELECT * FROM tb_newbee_mall_order_item WHERE order_id=#{oid}")
    public  List<TbNewbeeMallOrderItem> tbListItems(@Param("oid") Integer oid);


    //根据uid查询我的订单
    @Select("SELECT i.* FROM tb_newbee_mall_order_item i WHERE i.order_id IN (SELECT o.order_id FROM tb_newbee_mall_order o WHERE o.user_id = #{userId})")
    public List<TbNewbeeMallOrderItem> findByUidList(@Param("userId") Long userId);

    public List<TbNewbeeMallOrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

}
