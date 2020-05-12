package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.tb_seckill_order;
import com.example.newbeemall.utils.Result;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;

public interface TbSeckillOrderService extends IService<tb_seckill_order> {
    public int deleteSeckill(@Param("userId") long userId, @Param("goodsId") long goodsId);

    public int saveTbSeckillOrder(Result result , HttpServletRequest request);
}
