package com.example.newbeemall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public class TbNewbeeMallOrderItem implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 订单关联购物项主键id
     */
      @TableId(value = "order_item_id", type = IdType.AUTO)
    private Long orderItemId;

    /**
     * 订单主键id
     */
    private Long orderId;

    /**
     * 关联商品id
     */
    private Long goodsId;

    /**
     * 下单时商品的名称(订单快照)
     */
    private String goodsName;

    /**
     * 下单时商品的主图(订单快照)
     */
    private String goodsCoverImg;

    /**
     * 下单时商品的价格(订单快照)
     */
    private Integer sellingPrice;

    /**
     * 数量(订单快照)
     */
    private Integer goodsCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    //,o.order_status,o.pay_type,o.order_no  ,o.total_price

    @TableField(exist = false)
    private double totalPrice;
    @TableField(exist = false)
    private Integer orderStatus;
    /**
     * 订单号
     */
    @TableField(exist = false)
    private String orderNo;
    /**
     * 支付方式
     */
    @TableField(exist = false)
    private Integer payType;
    /**
     * 订单状态
     */
    @TableField(exist = false)
    private String orderStatusString;

    /**
     * 支付状态
     */
    @TableField(exist = false)
    private String payTypeString;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public String getPayTypeString() {
        return payTypeString;
    }

    public void setPayTypeString(String payTypeString) {
        this.payTypeString = payTypeString;
    }

    /**
     * 购物车序号
     */
    @TableField(exist = false)
    private Long cartItemId;




    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TbNewbeeMallOrderItem{" +
        "orderItemId=" + orderItemId +
        ", orderId=" + orderId +
        ", goodsId=" + goodsId +
        ", goodsName=" + goodsName +
        ", goodsCoverImg=" + goodsCoverImg +
        ", sellingPrice=" + sellingPrice +
        ", goodsCount=" + goodsCount +
        ", createTime=" + createTime +
        "}";
    }
}
