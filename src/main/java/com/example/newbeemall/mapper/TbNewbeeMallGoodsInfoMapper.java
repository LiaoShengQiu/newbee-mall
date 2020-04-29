package com.example.newbeemall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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

    @Update({"<script>update tb_newbee_mall_goods_info " +
            "set goods_sell_status = 0 " +
            "where goods_id in " +
            "<foreach collection ='ids' item ='id' separator=',' open='(' close=')'  > " +
            " #{id} " +
            "</foreach></script>"})
    public void upGoods(@Param("ids") Integer[] ids);


    @Update({"<script>update tb_newbee_mall_goods_info " +
            "set goods_sell_status = 1 " +
            "where goods_id in " +
            "<foreach collection ='ids' item ='id' separator=',' open='(' close=')'  > " +
            " #{id} " +
            "</foreach></script>"})
    public void downGoods(@Param("ids") Integer[] ids);

    /**
     * 后面修改田荣的
     * @param map
     * @return
     */
    public List<TbNewbeeMallGoodsInfo> selectByMap2(Map<String,Object> map);
}
