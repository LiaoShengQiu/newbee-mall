package com.example.newbeemall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public class TbNewbeeMallOrder implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 订单表主键id
     */
      @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 订单总价
     */
    private Integer totalPrice;

    /**
     * 支付状态:0.未支付,1.支付成功,-1:支付失败
     */
    private Integer payStatus;

    /**
     * 0.无 1.支付宝支付 2.微信支付
     */
    private Integer payType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
     */
    private Integer orderStatus;

    /**
     * 订单body
     */
    private String extraInfo;

    /**
     * 收货人姓名
     */
    private String userName;

    /**
     * 收货人手机号
     */
    private String userPhone;

    /**
     * 收货人收货地址
     */
    private String userAddress;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Integer isDeleted;



    @TableField(exist = false)
    private List<TbNewbeeMallOrderItem> orderItems;
    /**
     * 0.无 1.支付宝支付 2.微信支付
     */
    @TableField(exist = false)
    private String payTypeString;

    /**
     * 订单状态
     */
    @TableField(exist = false)
    private String orderStatusString;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    /**
     * 最新修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


    @TableField(exist = false)
    private List<TbNewbeeMallOrder> userList = new ArrayList<TbNewbeeMallOrder>();

    public String getOrderStatusString() {
        if(this.orderStatusString==null){
            this.orderStatusString= this.orderStatus==0?"待支付":this.orderStatus==1?"已支付":this.orderStatus==2?"配货完成":this.orderStatus==3?"出库成功":this.orderStatus==4?"交易成功":this.orderStatus==-1?"手动关闭":this.orderStatus==-2?"超时关闭":"商家关闭";
        }
        return orderStatusString;
    }


    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public List<TbNewbeeMallOrder> getUserList() {
        return userList;
    }

    public void setUserList(List<TbNewbeeMallOrder> userList) {
        this.userList = userList;
    }

    public String getPayTypeString() {
        return payTypeString;
    }

    public void setPayTypeString(String payTypeString) {
        this.payTypeString = payTypeString;
    }

    public List<TbNewbeeMallOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbNewbeeMallOrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }


    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TbNewbeeMallOrder{" +
        "orderId=" + orderId +
        ", orderNo=" + orderNo +
        ", userId=" + userId +
        ", totalPrice=" + totalPrice +
        ", payStatus=" + payStatus +
        ", payType=" + payType +
        ", payTime=" + payTime +
        ", orderStatus=" + orderStatus +
        ", extraInfo=" + extraInfo +
        ", userName=" + userName +
        ", userPhone=" + userPhone +
        ", userAddress=" + userAddress +
        ", isDeleted=" + isDeleted +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
