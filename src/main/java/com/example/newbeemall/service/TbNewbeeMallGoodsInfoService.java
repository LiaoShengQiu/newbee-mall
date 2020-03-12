package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallGoodsInfoService extends IService<TbNewbeeMallGoodsInfo> {

    /**
     * 商品搜索
     */
    PageResult searchsp(PageQueryUtil pageUtil);
}
