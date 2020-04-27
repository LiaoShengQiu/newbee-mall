package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallIndexConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import com.example.newbeemall.vo.NewBeeMallIndexConfigGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TbNewbeeMallIndexConfigService extends IService<TbNewbeeMallIndexConfig> {

    /**
     * 返回首页显示商品信息的数量
     */
    List<NewBeeMallIndexConfigGoodsVO> getIndex(int configType, int number);

    PageResult getConfigsPage(PageQueryUtil pageUtil);

    boolean deleteIds(Integer[] ids);
}
