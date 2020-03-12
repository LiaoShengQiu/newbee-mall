package com.example.newbeemall.service.impl;

import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsInfoMapper;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
@CacheConfig(cacheNames = "TbNewbeeMallGoodsInfoService")
public class TbNewbeeMallGoodsInfoServiceImpl extends ServiceImpl<TbNewbeeMallGoodsInfoMapper, TbNewbeeMallGoodsInfo> implements TbNewbeeMallGoodsInfoService {
@Resource
private TbNewbeeMallGoodsInfoMapper tbNewbeeMallGoodsInfoMapper;
    @Override
    public List<TbNewbeeMallGoodsInfo> listGoods(int start,int end) {
        return tbNewbeeMallGoodsInfoMapper.listGoods(start, end);
    }

    @Override
    public int count(int start, int end) {
        return tbNewbeeMallGoodsInfoMapper.count(start, end);
    }

    @Override
    //配置缓存
    @Cacheable(value = "getTbNewbeeMallGoodsInfo",keyGenerator = "keyGenerator")
    public TbNewbeeMallGoodsInfo findByid(long id) {
        return tbNewbeeMallGoodsInfoMapper.selectById(id);
    }
}
