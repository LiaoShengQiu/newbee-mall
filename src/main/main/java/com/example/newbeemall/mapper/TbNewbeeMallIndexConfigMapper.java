package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallIndexConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbNewbeeMallIndexConfigMapper extends BaseMapper<TbNewbeeMallIndexConfig> {

    List<TbNewbeeMallIndexConfig> getIndex(@Param("configType") int configType, @Param("number") int number);

}
