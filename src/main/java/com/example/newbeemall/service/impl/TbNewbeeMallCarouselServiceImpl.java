package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallCarousel;
import com.example.newbeemall.mapper.TbNewbeeMallCarouselMapper;
import com.example.newbeemall.service.TbNewbeeMallCarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.utils.BeanUtil;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import com.example.newbeemall.vo.NewBeeMallIndexCarouselVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
public class TbNewbeeMallCarouselServiceImpl extends ServiceImpl<TbNewbeeMallCarouselMapper, TbNewbeeMallCarousel> implements TbNewbeeMallCarouselService {


    @Resource
    private TbNewbeeMallCarouselMapper carouselMapper;

    @Override
    public List<NewBeeMallIndexCarouselVO> getCarouselsForIndex(int number) {
        List<NewBeeMallIndexCarouselVO> cVO = new ArrayList<>(number);
        List<TbNewbeeMallCarousel> carousel = carouselMapper.CarouselNum(number);
        if (!CollectionUtils.isEmpty(carousel)) {
            cVO = BeanUtil.copyList(carousel, NewBeeMallIndexCarouselVO.class);
        }
        return cVO;
    }

    @Override
    public PageResult getCarouselPage(PageQueryUtil pageUtil) {
        List<TbNewbeeMallCarousel> carousel = carouselMapper.getCarouselPage(pageUtil);
        int total = carouselMapper.getCarouselCount();
        return new PageResult(carousel,total,pageUtil.getLimit(),pageUtil.getPage());
    }

    @Override
    public boolean deleteIds(Integer[] ids) {
        return carouselMapper.deleteIds(ids) > 0?true:false;
    }

    @Override
    public List<TbNewbeeMallCarousel> indexList() {
        return carouselMapper.indexList();
    }
}
