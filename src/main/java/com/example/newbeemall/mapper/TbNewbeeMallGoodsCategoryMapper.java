package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallGoodsCategoryMapper extends BaseMapper<TbNewbeeMallGoodsCategory> {

    List<TbNewbeeMallGoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds,
                                                                       @Param("categoryLevel") int categoryLevel,
                                                                       @Param("number") int number);



}
