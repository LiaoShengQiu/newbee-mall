package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //    @Override
//    public List<TbNewbeeMallGoodsInfo> getList(Integer page, Integer limit) {
//        Page<TbNewbeeMallGoodsInfo> cupage = new Page<TbNewbeeMallGoodsInfo>(page,limit);
//        List<TbNewbeeMallGoodsInfo> list = new ArrayList<>();
//        IPage<TbNewbeeMallGoodsInfo> mapIPage = goodsInfoMapper.selectPage(cupage,null);
//        list = mapIPage.getRecords();
//
//        return list;
//    }


    @Override
    public Map<String,Object> getList(Integer page, Integer limit) {
        Page<TbNewbeeMallGoodsInfo> cupage = new Page<TbNewbeeMallGoodsInfo>(page,limit);
        List<TbNewbeeMallGoodsInfo> list = new ArrayList<>();
        IPage<TbNewbeeMallGoodsInfo> mapIPage = goodsInfoMapper.selectPage(cupage,null);
        list = mapIPage.getRecords();
        Map<String,Object> maplist = new HashMap<String,Object>();
        maplist.put("list",list);
        maplist.put("currPage",mapIPage.getCurrent());
        maplist.put("totalPage",mapIPage.getPages());
        maplist.put("totalCount",mapIPage.getTotal());
        return maplist;
    }

    @Override
    public void upGoods(Integer[] ids) {
        goodsInfoMapper.upGoods(ids);
    }

    @Override
    public void downGoods(Integer[] ids) {
        goodsInfoMapper.downGoods(ids);
    }

}
