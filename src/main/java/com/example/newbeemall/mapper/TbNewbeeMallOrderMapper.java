package com.example.newbeemall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface TbNewbeeMallOrderMapper extends BaseMapper<TbNewbeeMallOrder> {

    List<TbNewbeeMallOrder> findOrderByOrderId(@Param("userId") Long userId);

    int insertSelective(TbNewbeeMallOrder order);

    TbNewbeeMallOrder findOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     *修改订单状态
     * @param order
     * @return
     */
    int updatePayType(TbNewbeeMallOrder order);

    //查询条件 page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
    public List<TbNewbeeMallOrder> order_list(Map<String, Object> map);
    public PageResult myordersItems_list(Long userId, PageQueryUtil pageQueryUtil);
    public List<TbNewbeeMallOrder> findNewBeeMallOrderList(PageQueryUtil pageQueryUtil);
    public int count(PageQueryUtil pageQueryUtil);
    @Select("SELECT o.order_id FROM tb_newbee_mall_order o WHERE o.user_id = 1")
    public List<Long> findbyUid_order_ids(@Param("userId") Long userId);
    //修改
    public int updataOrder(@Param("map") Map<String, Object> map);

    //2 配货 、 3出库
    public int updateStatus(List<Integer> list, @Param("status") Integer status);
}
