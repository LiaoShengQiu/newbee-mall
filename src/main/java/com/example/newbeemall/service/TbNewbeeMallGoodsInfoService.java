package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallGoodsInfoService extends IService<TbNewbeeMallGoodsInfo> {
    public List<TbNewbeeMallGoodsInfo> listGoods(int start, int end);
    public int count(int start, int end);

    public TbNewbeeMallGoodsInfo findByid(long id);
}
