package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.entity.TbSeckillGoods;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsInfoMapper;
import com.example.newbeemall.mapper.TbSeckillGoodsMapper;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import com.example.newbeemall.service.TbSeckillGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class TbSeckillGoodsServiceImpl  extends ServiceImpl<TbSeckillGoodsMapper, TbSeckillGoods> implements TbSeckillGoodsService {
  @Resource
    private TbSeckillGoodsMapper tbSeckillGoodsMapper;
    @Resource
    private TbNewbeeMallGoodsInfoService tbNewbeeMallGoodsInfoService;
  @Resource
    private TbNewbeeMallGoodsInfoMapper tbNewbeeMallGoodsInfoMapper;
    // 秒杀商品后减少库存
    @Override
    public void modMiaosha(Long goodsId) {
//从数据库里获取商品的数量（根据id查询）
        TbNewbeeMallGoodsInfo tbNewbeeMallGoodsInfo = tbNewbeeMallGoodsInfoMapper.selectById(goodsId);
        int count = tbNewbeeMallGoodsInfo.getStockNum();
        TbNewbeeMallGoodsInfo tbinfo = new TbNewbeeMallGoodsInfo();
        tbinfo.setStockNum(count);
        tbinfo.setGoodsId(goodsId);
        boolean b = tbNewbeeMallGoodsInfoService.saveOrUpdate(tbinfo);
        System.out.println("修改结果为："+b);
    }
    // 秒杀商品前判断是否有库存
    @Override
    public boolean isorNo(Integer goodsId) {
        //从数据库里获取商品的数量（根据id查询）
        TbNewbeeMallGoodsInfo tbNewbeeMallGoodsInfo = tbNewbeeMallGoodsInfoMapper.selectById(goodsId);
       int count = tbNewbeeMallGoodsInfo.getStockNum();
       if(count > 0){
           return true;
       }
         return false;
    }
}
