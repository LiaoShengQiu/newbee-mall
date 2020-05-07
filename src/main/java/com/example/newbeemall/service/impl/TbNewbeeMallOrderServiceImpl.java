package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.entity.*;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsInfoMapper;
import com.example.newbeemall.mapper.TbNewbeeMallOrderItemMapper;
import com.example.newbeemall.mapper.TbNewbeeMallOrderMapper;
import com.example.newbeemall.mapper.TbNewbeeMallShoppingCartItemMapper;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import com.example.newbeemall.utils.NewBeeMallOrderStatusEnum;
import com.example.newbeemall.utils.NumberUtil;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private TbNewbeeMallOrderMapper orderMapper;
    @Resource
    private TbNewbeeMallOrderItemMapper itemMapper;
    @Resource
    private TbNewbeeMallShoppingCartItemMapper cartItemMapper;
    @Resource
    private TbNewbeeMallGoodsInfoMapper goodsInfoMapper;
    @Override
    public List<TbNewbeeMallOrder> findOrderByOrderId(Long userId) {
        return orderMapper.findOrderByOrderId(userId);
    }

    @Transactional
    @Override
    public Long saveOrder(TbNewbeeMallUser user, List<TbNewbeeMallOrderItem> orders) {
        Long orderId = 0L;
        try{

                synchronized (this){
                    //添加订单详情项
                    for (TbNewbeeMallOrderItem item:orders) {
                        TbNewbeeMallGoodsInfo goodsInfo = goodsInfoMapper.selectById(item.getGoodsId());
                        if(goodsInfo.getStockNum()<item.getGoodsCount()){
                            throw new Exception("商品数量不够了");
                        }
                    }
                    String orderNo = NumberUtil.genOrderNo();
                    TbNewbeeMallOrder order = new TbNewbeeMallOrder();
                    order.setOrderNo(orderNo);
                    order.setUserId(user.getUserId());
                    order.setUserAddress(user.getAddress());
                    int priceTotal = 0;
                    for (TbNewbeeMallOrderItem item:orders) {
                        priceTotal+=item.getGoodsCount()*item.getSellingPrice();
                    }
                    order.setTotalPrice(priceTotal);
                    //订单描述信息
                    order.setExtraInfo("");
                    //返回大于0代表插入成功
                    if(orderMapper.insertSelective(order) > 0) {
                        for (TbNewbeeMallOrderItem item : orders) {
                            item.setOrderId(order.getOrderId());
                            itemMapper.insert(item);
                        }
                        //删除购物车商品
                        UpdateWrapper<TbNewbeeMallShoppingCartItem> wrapper = new UpdateWrapper<TbNewbeeMallShoppingCartItem>();
                        wrapper.eq("user_id",user.getUserId());
                        cartItemMapper.delete(wrapper);
                    }
                    orderId=order.getOrderId();
                }
        } catch (Exception e){
            e.printStackTrace();
        }
        return orderId;
    }

    @Override
    public TbNewbeeMallOrder findOrderByOrderNo(String orderNo) {
        return orderMapper.findOrderByOrderNo(orderNo);
    }

    @Override
    public boolean updatePayType(TbNewbeeMallOrder order) {
        return orderMapper.updatePayType(order)>0?true:false;
    }

    @Override
    public PageResult order_list(PageQueryUtil pageQueryUtil) {
        List<TbNewbeeMallOrder> tbNewbeeMallOrders = orderMapper.order_list(pageQueryUtil);
        int count = orderMapper.order_count();
        PageResult result = new PageResult(tbNewbeeMallOrders,count,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
        return result;
    }

    @Override
    public PageResult myordersItems_list(Long userId, PageQueryUtil pageQueryUtil) {
        List<TbNewbeeMallOrder> newBeeMallOrders = orderMapper.findNewBeeMallOrderList(pageQueryUtil);
        int total = orderMapper.count(pageQueryUtil);    //总记录数
        if(total > 0){
            //设置订单状态中文显示值
            for (TbNewbeeMallOrder order : newBeeMallOrders) {
                order.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(order.getOrderStatus()).getName());
            }
            //获取上面查到的orderIds   limit后的
            List<Long> orderIds = newBeeMallOrders.stream().map(TbNewbeeMallOrder::getOrderId).collect(Collectors.toList());
            System.out.println("orderIds"+orderIds.toString());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<TbNewbeeMallOrderItem> orderItems = itemMapper.selectByOrderIds(orderIds);
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
        return orderMapper.count(pageQueryUtil);
    }

    @Override
    public List<Long> findbyUid_order_ids(Long userId) {
        return orderMapper.findbyUid_order_ids(userId);
    }

    @Override
    public int updataOrder(Map<String, Object> map) {
        return orderMapper.updataOrder(map);
    }

    @Override
    public int updateStatus(Integer[] list, Integer status) {
        return orderMapper.updateStatus(list,status);
    }
}
