package com.example.newbeemall.service.impl;

import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsInfoMapper;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.utils.BeanUtil;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import com.example.newbeemall.vo.NewBeeMallSearchGoodsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TbNewbeeMallGoodsInfoServiceImpl extends ServiceImpl<TbNewbeeMallGoodsInfoMapper, TbNewbeeMallGoodsInfo> implements TbNewbeeMallGoodsInfoService {

    @Resource
    private TbNewbeeMallGoodsInfoMapper goodsInfoMapper;



    @Override
    public PageResult searchsp(PageQueryUtil pageUtil) {
        List<TbNewbeeMallGoodsInfo> goodsList = goodsInfoMapper.findGoodsInfo(pageUtil);
        int total = goodsInfoMapper.getCount(pageUtil);
        List<NewBeeMallSearchGoodsVO> gVO = new ArrayList<>();
        if (goodsList != null) {
            gVO = BeanUtil.copyList(goodsList, NewBeeMallSearchGoodsVO.class);
        }
        PageResult pageResult = new PageResult(gVO, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
