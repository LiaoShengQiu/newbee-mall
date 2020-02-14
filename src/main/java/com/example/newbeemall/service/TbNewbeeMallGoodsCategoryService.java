package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.vo.NewBeeMallIndexCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallGoodsCategoryService extends IService<TbNewbeeMallGoodsCategory> {

    TbNewbeeMallGoodsCategory getGoodsCategoryById(Long id);

    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<NewBeeMallIndexCategoryVO> getCategoriesForIndex();

    /**
     * 根据parentId和level获取分类列表
     */
    List<TbNewbeeMallGoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);

}
