package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallIndexConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.vo.NewBeeMallIndexConfigGoodsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallIndexConfigService extends IService<TbNewbeeMallIndexConfig> {

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     */
    List<NewBeeMallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);

}
