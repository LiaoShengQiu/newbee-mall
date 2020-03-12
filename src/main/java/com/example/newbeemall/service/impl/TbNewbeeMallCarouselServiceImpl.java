package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallCarousel;
import com.example.newbeemall.mapper.TbNewbeeMallCarouselMapper;
import com.example.newbeemall.service.TbNewbeeMallCarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
public class TbNewbeeMallCarouselServiceImpl extends ServiceImpl<TbNewbeeMallCarouselMapper, TbNewbeeMallCarousel> implements TbNewbeeMallCarouselService {

    @Resource
    private TbNewbeeMallCarouselMapper carouselMapper;


}
