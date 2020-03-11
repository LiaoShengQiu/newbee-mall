package com.example.newbeemall;

import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class NewbeeMallApplicationTests {
@Resource
    TbNewbeeMallGoodsInfoService tbNewbeeMallGoodsInfoService;
    @Test
    void contextLoads() {
        TbNewbeeMallGoodsInfo info = tbNewbeeMallGoodsInfoService.findByid(10038);
        System.out.println(info);
        TbNewbeeMallGoodsInfo info2 = tbNewbeeMallGoodsInfoService.findByid(10038);
        System.out.println(info2);
    }

}
