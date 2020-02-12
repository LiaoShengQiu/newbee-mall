package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallGoodsCategoryService extends IService<TbNewbeeMallGoodsCategory> {

    List<TbNewbeeMallGoodsCategory> findGoodsCategory(Map<String,Object> map);
}
