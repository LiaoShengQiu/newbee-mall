package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;

import java.util.Map;

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
    PageResult searchsp2(PageQueryUtil pageUtil);


    //以下是后台的
    public Map<String,Object> getList(Integer page, Integer limit);

    public void upGoods(Integer[] ids);

    public void downGoods(Integer[] ids);
}
