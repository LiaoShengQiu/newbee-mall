package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallCarousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.vo.NewBeeMallIndexCarouselVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallCarouselService extends IService<TbNewbeeMallCarousel> {

    /**
     * 返回固定数量的轮播图对象(首页调用)
     */
    List<NewBeeMallIndexCarouselVO> getCarouselsForIndex(int number);

}
