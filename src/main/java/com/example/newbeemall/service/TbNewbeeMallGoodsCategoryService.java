package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;

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
	
	boolean save(Map<String,Object> map);
	
	boolean update(Map<String,Object> map);
	
	boolean delete();

	List<TbNewbeeMallGoodsCategory> findYiji();


	List<TbNewbeeMallGoodsCategory> finderji(List<Long> ids);
}
