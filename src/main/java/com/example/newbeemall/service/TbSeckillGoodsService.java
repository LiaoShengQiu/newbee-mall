package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.TbSeckillGoods;

public interface TbSeckillGoodsService extends IService<TbSeckillGoods> {
    // 秒杀商品后减少库存
    public void modMiaosha(Long goodsId);
    // 秒杀商品前判断是否有库存
    public boolean isorNo(Integer goodsId);
}
