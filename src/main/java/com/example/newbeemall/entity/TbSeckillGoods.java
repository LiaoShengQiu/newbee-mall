package com.example.newbeemall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TbSeckillGoods implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 秒杀表主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    private int goodsId;

    private Integer itemId;

    private String title;

    private String small_pic;

    private double price;

    private double costPrice;

    private String sellerId;

    private Date createTime;

    private Date checkTime;

    private String status;

    private Date startTime;

    private Date endTime;

    private int num;

    private  int stockCount;

    private String introduction;
}
