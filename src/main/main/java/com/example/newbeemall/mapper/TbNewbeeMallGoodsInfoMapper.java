package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.utils.PageQueryUtil;

import java.util.List;

public interface TbNewbeeMallGoodsInfoMapper extends BaseMapper<TbNewbeeMallGoodsInfo> {

    List<TbNewbeeMallGoodsInfo> getById(List<Long> goodsIds);

    List<TbNewbeeMallGoodsInfo> findGoodsInfo(PageQueryUtil pageUtil);

    int getCount(PageQueryUtil pageUtil);

}
