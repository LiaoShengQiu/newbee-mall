package com.example.newbeemall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallGoodsCategoryMapper extends BaseMapper<TbNewbeeMallGoodsCategory> {

    //查询条件parentId=父级，categoryLevel=分类等级, page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
    List<TbNewbeeMallGoodsCategory> findGoodsCategoryPage(Map<String,Object> map);
	//新增分类
	int add(Map<String,Object> map);

	int update(Map<String,Object> map);

	//一级
	@Select("SELECT * FROM  tb_newbee_mall_goods_category WHERE  parent_id = 0")
	List<TbNewbeeMallGoodsCategory> findYiji();

	//二级、三级
	List<TbNewbeeMallGoodsCategory> findersanji(List<Long> id);

}
