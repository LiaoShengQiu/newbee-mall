package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallIndexConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbNewbeeMallIndexConfigMapper extends BaseMapper<TbNewbeeMallIndexConfig> {

    List<TbNewbeeMallIndexConfig> getIndex(@Param("configType") int configType, @Param("number") int number);

    /**
     * 商品后台首页配置
     * @param configType
     * @return
     */
    List<TbNewbeeMallIndexConfig> getConfigType(@Param("configType") Integer configType);

    int getTotalIndexConfigs(PageQueryUtil pageUtil);

    List<TbNewbeeMallIndexConfig> findIndexConfigList(PageQueryUtil pageUtil);

    int deleteIds(@Param("ids")Integer[] ids);
}
