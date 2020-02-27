package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallOrderService extends IService<TbNewbeeMallOrder> {
    public List<TbNewbeeMallOrder> order_list(Map<String,Object> map);

    public PageResult myordersItems_list(Long userId, PageQueryUtil pageQueryUtil);
    public List<TbNewbeeMallOrder> findNewBeeMallOrderList(PageQueryUtil pageQueryUtil);
    public int count(PageQueryUtil pageQueryUtil);
    public List<Long> findbyUid_order_ids( Long userId);

    //修改
    public int updataOrder(Map<String,Object> map);
    //2 配货 、 3出库
    public int updateStatus(List<Integer> list, Integer status);
}
