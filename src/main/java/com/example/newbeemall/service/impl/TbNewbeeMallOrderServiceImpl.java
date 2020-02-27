package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.mapper.TbNewbeeMallOrderItemMapper;
import com.example.newbeemall.mapper.TbNewbeeMallOrderMapper;
import com.example.newbeemall.service.TbNewbeeMallOrderItemService;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import com.example.newbeemall.utils.NewBeeMallOrderStatusEnum;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
public class TbNewbeeMallOrderServiceImpl extends ServiceImpl<TbNewbeeMallOrderMapper, TbNewbeeMallOrder> implements TbNewbeeMallOrderService {
 @Resource
   private  TbNewbeeMallOrderMapper tbNewbeeMallOrderMapper;

 @Resource
  private TbNewbeeMallOrderItemService tbNewbeeMallOrderItemService;
    @Resource

    private TbNewbeeMallOrderItemMapper tbNewbeeMallOrderItemMapper;
    @Override
    public List<TbNewbeeMallOrder> order_list(Map<String,Object> map) {
        return tbNewbeeMallOrderMapper.order_list(map);
    }

    @Override
    public PageResult myordersItems_list(Long userId, PageQueryUtil pageQueryUtil) {
        List<TbNewbeeMallOrder> newBeeMallOrders = tbNewbeeMallOrderMapper.findNewBeeMallOrderList(pageQueryUtil);
        int total = tbNewbeeMallOrderMapper.count(pageQueryUtil);    //总记录数
        if(total > 0){
            //设置订单状态中文显示值
            for (TbNewbeeMallOrder order : newBeeMallOrders) {
                order.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(order.getOrderStatus()).getName());
            }
            //获取上面查到的orderIds   limit后的
            List<Long> orderIds = newBeeMallOrders.stream().map(TbNewbeeMallOrder::getOrderId).collect(Collectors.toList());
            System.out.println("orderIds"+orderIds.toString());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<TbNewbeeMallOrderItem> orderItems = tbNewbeeMallOrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<TbNewbeeMallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(TbNewbeeMallOrderItem::getOrderId));
                System.out.println("itemByOrderIdMap=========="+itemByOrderIdMap);
                for (TbNewbeeMallOrder newBeeMallOrderListVO : newBeeMallOrders) {
                    System.out.println("newBeeMallOrderListVO.getOrderId()"+newBeeMallOrderListVO.getOrderId());
                    //判断是否存在
                    if (itemByOrderIdMap.containsKey(newBeeMallOrderListVO.getOrderId())) {
                   // 在集合里面根据orderId 查找
                        List<TbNewbeeMallOrderItem> orderItemListTemp = itemByOrderIdMap.get(newBeeMallOrderListVO.getOrderId());
                        System.out.println("===============================");
                        System.out.println("=============="+orderItemListTemp.toString());
                        //将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
                       newBeeMallOrderListVO.setOrderItems(orderItemListTemp);
                    }
                }
            }
        }

        PageResult pageResult = new PageResult(newBeeMallOrders, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public List<TbNewbeeMallOrder> findNewBeeMallOrderList(PageQueryUtil pageQueryUtil) {
        return null;
    }

    @Override
    public int count(PageQueryUtil pageQueryUtil) {
        return tbNewbeeMallOrderMapper.count(pageQueryUtil);
    }

    @Override
    public List<Long> findbyUid_order_ids(Long userId) {
        return tbNewbeeMallOrderMapper.findbyUid_order_ids(userId);
    }

    @Override
    public int updataOrder(Map<String, Object> map) {
        return tbNewbeeMallOrderMapper.updataOrder(map);
    }

    @Override
    public int updateStatus(List<Integer> list, Integer status) {
        return tbNewbeeMallOrderMapper.updateStatus(list,status);
    }
}
