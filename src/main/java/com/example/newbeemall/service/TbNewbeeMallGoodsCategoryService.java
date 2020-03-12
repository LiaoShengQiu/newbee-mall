package com.example.newbeemall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.vo.NewBeeMallIndexCategoryVO;
import com.example.newbeemall.vo.SearchPageCategoryVO;

import java.util.List;
import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import java.util.Map;

public interface TbNewbeeMallGoodsCategoryService extends IService<TbNewbeeMallGoodsCategory> {

    TbNewbeeMallGoodsCategory getGoodsCategoryById(Long id);

    /**
     * 返回首页分类数据
     */
    List<NewBeeMallIndexCategoryVO> CategoryIndex();

    /**
     * 返回分类数据
     */
    SearchPageCategoryVO getCategoryById(Long categoryId);


    /**
     * 根据parentId和level获取分类列表
     */
    List<TbNewbeeMallGoodsCategory> GoodsList(List<Long> parentIds, int categoryLevel);

    List<TbNewbeeMallGoodsCategory> findGoodsCategory(Map<String, Object> map);
	
	boolean save(Map<String, Object> map);
	
	boolean update(Map<String, Object> map);
	
	boolean delete();

	List<TbNewbeeMallGoodsCategory> findYiji();

	List<TbNewbeeMallGoodsCategory> finderji(List<Long> ids);
}
