package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.entity.tb_seckill_order;
import com.example.newbeemall.mapper.TbSeckillOrderMapper;
import com.example.newbeemall.service.TbSeckillOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TbSeckillOrderServiceImpl   extends ServiceImpl<TbSeckillOrderMapper, tb_seckill_order> implements TbSeckillOrderService {
  @Resource
  private TbSeckillOrderMapper tbSeckillOrderMapper;
    @Override
    public int deleteSeckill(long userId, long goodsId) {
        return tbSeckillOrderMapper.deleteSeckill(userId, goodsId);
    }
}
