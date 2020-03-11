package com.example.newbeemall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.entity.tb_seckill_order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface TbSeckillOrderMapper extends BaseMapper<tb_seckill_order> {
    @Delete("DELETE FROM tb_seckill_order WHERE user_id = #{userId} AND seckill_id=(SELECT g.id FROM tb_seckill_goods g WHERE goods_id=#{goodsId})")
    public int deleteSeckill(@Param("userId") long userId, @Param("goodsId") long goodsId);
}
