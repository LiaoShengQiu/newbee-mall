package com.example.newbeemall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class tb_seckill_order implements Serializable {
    /**
     * 秒杀成功后的表主键id
     */
    @TableId(value = "soid", type = IdType.AUTO)
    private long soid;

    private int seckillId;

    private double money;

    private long userId;

    private String sellerId;

    private Date createTime;

    private Date payTime;

    private String status;

    private String receiverAdd;

    private String receiverMob;

    private String receiver;

    private String transactionId;
}
