package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsCategoryMapper;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.utils.BeanUtil;
import com.example.newbeemall.vo.NewBeeMallIndexCategoryVO;
import com.example.newbeemall.vo.SearchPageCategoryVO;
import com.example.newbeemall.vo.SecondLevelCategoryVO;
import com.example.newbeemall.vo.ThirdLevelCategoryVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class TbNewbeeMallGoodsCategoryServiceImpl extends ServiceImpl<TbNewbeeMallGoodsCategoryMapper, TbNewbeeMallGoodsCategory> implements TbNewbeeMallGoodsCategoryService {


    @Resource
    private TbNewbeeMallGoodsCategoryMapper goodsCategoryMapper;

    private TbNewbeeMallGoodsCategoryService goodsCategoryService;


    @Override
    public TbNewbeeMallGoodsCategory getGoodsCategoryById(Long id) {
        return goodsCategoryMapper.selectById(id);
    }

    @Override
    public List<NewBeeMallIndexCategoryVO> CategoryIndex() {
        List<NewBeeMallIndexCategoryVO> cVO = new ArrayList<>();
        //获取一级分类的固定数量的数据
        List<TbNewbeeMallGoodsCategory> yiList = goodsCategoryMapper.selectGoodsCategory(Collections.singletonList(0L), 1, 10);
        for(TbNewbeeMallGoodsCategory gc : yiList){
            System.out.println(gc.getCategoryName());
        }
        if (yiList != null) {
            List<Long> yiIds = yiList.stream().map(TbNewbeeMallGoodsCategory::getCategoryId).collect(Collectors.toList());
            //获取二级分类的数据
            List<TbNewbeeMallGoodsCategory> secondLevelCategories = goodsCategoryMapper.selectGoodsCategory(yiIds, 2, 0);
            if (secondLevelCategories != null) {
                List<Long> secondLevelCategoryIds = secondLevelCategories.stream().map(TbNewbeeMallGoodsCategory::getCategoryId).collect(Collectors.toList());
                //获取三级分类的数据
                List<TbNewbeeMallGoodsCategory> thirdLevelCategories = goodsCategoryMapper.selectGoodsCategory(secondLevelCategoryIds, 3, 0);
                if (thirdLevelCategories != null) {
                    //根据 parentId 将 thirdLevelCategories 分组
                    Map<Long, List<TbNewbeeMallGoodsCategory>> thirdLevelCategoryMap = thirdLevelCategories.stream().collect(groupingBy(TbNewbeeMallGoodsCategory::getParentId));
                    List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
                    //处理二级分类
                    for (TbNewbeeMallGoodsCategory secondLevelCategory : secondLevelCategories) {
                        SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
                        BeanUtil.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                        //如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                            //根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                            List<TbNewbeeMallGoodsCategory> tempGoodsCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                            secondLevelCategoryVO.setThirdLevelCategoryVOS((BeanUtil.copyList(tempGoodsCategories, ThirdLevelCategoryVO.class)));
                            secondLevelCategoryVOS.add(secondLevelCategoryVO);
                        }
                    }
                    //一级分类
                    if (secondLevelCategoryVOS != null) {
                        //根据 parentId 将 thirdLevelCategories 分组
                        Map<Long, List<SecondLevelCategoryVO>> erMap = secondLevelCategoryVOS.stream().collect(groupingBy(SecondLevelCategoryVO::getParentId));
                        for (TbNewbeeMallGoodsCategory yiCa : yiList) {
                            NewBeeMallIndexCategoryVO icVO = new NewBeeMallIndexCategoryVO();
                            BeanUtil.copyProperties(yiCa, icVO);
                            //如果该一级分类下有数据则放入 newBeeMallIndexCategoryVOS 对象中
                            if (erMap.containsKey(yiCa.getCategoryId())) {
                                //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                                List<SecondLevelCategoryVO> temp = erMap.get(yiCa.getCategoryId());
                                icVO.setSecondLevelCategoryVOS(temp);
                                cVO.add(icVO);
                            }
                        }
                    }
                }
            }
            return cVO;
        } else {
            return null;
        }
    }



    @Override
    public SearchPageCategoryVO getCategoryById(Long categoryId) {
        SearchPageCategoryVO spcVO = new SearchPageCategoryVO();
        TbNewbeeMallGoodsCategory sanCategory = goodsCategoryMapper.getById(categoryId);
        if (sanCategory != null && sanCategory.getCategoryLevel() == 3) {
            //获取当前三级分类的二级分类
            TbNewbeeMallGoodsCategory erCategoryList = goodsCategoryMapper.getById(sanCategory.getParentId());
            if (erCategoryList != null && erCategoryList.getCategoryLevel() == 2) {
                //获取当前二级分类下的三级分类
                List<TbNewbeeMallGoodsCategory> sanCategoryList = goodsCategoryMapper.selectGoodsCategory(Collections.singletonList(erCategoryList.getCategoryId()), 3, 8);
                spcVO.setCurrentCategoryName(sanCategory.getCategoryName());
                spcVO.setSecondLevelCategoryName(erCategoryList.getCategoryName());
                spcVO.setThirdLevelCategoryList(sanCategoryList);
                return spcVO;
            }
        }
        return null;
    }

    @Override
    public List<TbNewbeeMallGoodsCategory> goodsList(List<Long> parentIds, int categoryLevel) {
        return goodsCategoryMapper.selectGoodsCategory(parentIds, categoryLevel, 0);//0代表查询所有
    }

    @Override
    public List<TbNewbeeMallGoodsCategory> findGoodsCategory(Map<String, Object> map) {
        return goodsCategoryMapper.findGoodsCategoryPage(map);
    }

	@Override
	public List<TbNewbeeMallGoodsCategory> findYiji() {
		return goodsCategoryService.findYiji();
	}

	@Override
	public List<TbNewbeeMallGoodsCategory> finderji(List<Long> ids) {
		return goodsCategoryService.finderji(ids);
	}

	@Override
	public boolean save(Map<String,Object> map){
	    return goodsCategoryMapper.add(map)==1;
	}
	
	@Override
	public boolean update(Map<String,Object> map){
		return goodsCategoryMapper.update(map)==1;
	};
	
	@Override
	public boolean delete(){
		return false;
	};

    @Override
    public List<TbNewbeeMallGoodsCategory> getFirstLevelCate() {
        QueryWrapper<TbNewbeeMallGoodsCategory> wrapper = new QueryWrapper<TbNewbeeMallGoodsCategory>();
        wrapper.eq("category_level",1);
        return goodsCategoryMapper.selectList(wrapper);
    }

    @Override
    public List<TbNewbeeMallGoodsCategory> getlistForSelect(Long id) {
        QueryWrapper<TbNewbeeMallGoodsCategory> wrapper = new QueryWrapper<TbNewbeeMallGoodsCategory>();
        wrapper.eq("parent_id",id);
        return goodsCategoryMapper.selectList(wrapper);
    }

    @Override
    public boolean isLevelTwo(Long id) {
        QueryWrapper<TbNewbeeMallGoodsCategory> wrapper = new QueryWrapper<TbNewbeeMallGoodsCategory>();
        wrapper.eq("category_id",id);
        wrapper.eq("category_level",1);
        return goodsCategoryMapper.selectOne(wrapper) != null;
    }

    @Override
    public Long findParentId(Long categoryId) {
        return goodsCategoryMapper.findParentId(categoryId);
    }
}
