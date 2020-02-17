package com.example.newbeemall.service.impl;

import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.entity.TbNewbeeMallIndexConfig;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsCategoryMapper;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsInfoMapper;
import com.example.newbeemall.mapper.TbNewbeeMallIndexConfigMapper;
import com.example.newbeemall.service.TbNewbeeMallIndexConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.utils.BeanUtil;
import com.example.newbeemall.vo.NewBeeMallIndexConfigGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
public class TbNewbeeMallIndexConfigServiceImpl extends ServiceImpl<TbNewbeeMallIndexConfigMapper, TbNewbeeMallIndexConfig> implements TbNewbeeMallIndexConfigService {

    @Resource
    private TbNewbeeMallIndexConfigMapper indexConfigMapper;
    @Resource
    private TbNewbeeMallGoodsInfoMapper goodsMapper;

    @Override
    public List<NewBeeMallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number) {
        List<NewBeeMallIndexConfigGoodsVO> newBeeMallIndexConfigGoodsVOS = new ArrayList<>(number);
        List<TbNewbeeMallIndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的goodsId
            List<Long> goodsIds = indexConfigs.stream().map(TbNewbeeMallIndexConfig::getGoodsId).collect(Collectors.toList());
            List<TbNewbeeMallGoodsInfo> newBeeMallGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
            newBeeMallIndexConfigGoodsVOS = BeanUtil.copyList(newBeeMallGoods, NewBeeMallIndexConfigGoodsVO.class);
        }
        return newBeeMallIndexConfigGoodsVOS;
    }
}
