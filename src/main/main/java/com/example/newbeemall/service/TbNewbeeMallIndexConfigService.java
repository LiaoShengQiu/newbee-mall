package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallIndexConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.vo.NewBeeMallIndexConfigGoodsVO;

import java.util.List;


public interface TbNewbeeMallIndexConfigService extends IService<TbNewbeeMallIndexConfig> {

    /**
     * 返回首页显示商品信息的数量
     */
    List<NewBeeMallIndexConfigGoodsVO> getIndex(int configType, int number);

}
