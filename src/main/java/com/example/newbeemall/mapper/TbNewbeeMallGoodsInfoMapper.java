package com.example.newbeemall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbNewbeeMallGoodsInfoMapper extends BaseMapper<TbNewbeeMallGoodsInfo> {

    List<TbNewbeeMallGoodsInfo> getById(List<Long> goodsIds);

    List<TbNewbeeMallGoodsInfo> findGoodsInfo(PageQueryUtil pageUtil);

    int getCount(PageQueryUtil pageUtil);

    //显示秒杀的商品
    @Select("SELECT *FROM tb_newbee_mall_goods_info WHERE goods_id IN (10037,10038) limit #{start},#{end}")
    public List<TbNewbeeMallGoodsInfo> listGoods(@Param("start") int start, @Param("end") int end);
    //总记录
    @Select("SELECT count(1) FROM tb_newbee_mall_goods_info WHERE goods_id IN (10037,10038) limit #{start},#{end}")
    public int count(@Param("start") int start,@Param("end") int end);

}
