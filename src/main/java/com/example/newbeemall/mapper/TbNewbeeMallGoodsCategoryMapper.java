package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface TbNewbeeMallGoodsCategoryMapper extends BaseMapper<TbNewbeeMallGoodsCategory> {

    List<TbNewbeeMallGoodsCategory> selectGoodsCategory(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);

    TbNewbeeMallGoodsCategory getById(Long categoryId);

    //查询条件parentId=父级，categoryLevel=分类等级, page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
    List<TbNewbeeMallGoodsCategory> findGoodsCategoryPage(Map<String,Object> map);

	//新增分类
	int add(Map<String,Object> map);

	int update(Map<String,Object> map);

    List<TbNewbeeMallGoodsCategory> findersanji(List<Long> ids);

    List<TbNewbeeMallGoodsCategory> findYiji();
    //根据分类id查询上级分类
    Long findParentId(@Param("categoryId")Long categoryId);

    int nodeCount(Map<String,Object> map);

}
