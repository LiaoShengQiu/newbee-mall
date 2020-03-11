package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.tb_seckill_order;
import org.apache.ibatis.annotations.Param;

public interface TbSeckillOrderService extends IService<tb_seckill_order> {
    public int deleteSeckill(@Param("userId") long userId, @Param("goodsId") long goodsId);
}
