package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;
import java.util.Map;

public interface TbNewbeeMallGoodsCategoryMapper extends BaseMapper<TbNewbeeMallGoodsCategory> {

    List<TbNewbeeMallGoodsCategory> selectGoodsCategory(@Param("parentIds") List<Long> parentIds,
                                                        @Param("categoryLevel") int categoryLevel,
                                                        @Param("number") int number);

    TbNewbeeMallGoodsCategory getById(Long categoryId);





    //查询条件parentId=父级，categoryLevel=分类等级, page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
    List<TbNewbeeMallGoodsCategory> findGoodsCategoryPage(Map<String,Object> map);

	//新增分类
	int add(Map<String,Object> map);

	int update(Map<String,Object> map);
}
