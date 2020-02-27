package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsCategoryMapper;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
public class TbNewbeeMallGoodsCategoryServiceImpl extends ServiceImpl<TbNewbeeMallGoodsCategoryMapper, TbNewbeeMallGoodsCategory> implements TbNewbeeMallGoodsCategoryService {

    @Resource
    private TbNewbeeMallGoodsCategoryMapper goodsCategoryMapper;

    @Override
    public List<TbNewbeeMallGoodsCategory> findGoodsCategory(Map<String,Object> map) {
        return goodsCategoryMapper.findGoodsCategoryPage(map);
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
	}

	@Override
	public List<TbNewbeeMallGoodsCategory> findYiji() {
		return goodsCategoryMapper.findYiji();
	}



	@Override
	public List<TbNewbeeMallGoodsCategory> finderji(List<Long> ids) {
		return goodsCategoryMapper.findersanji(ids);
	}


}
