package com.example.newbeemall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.service.TbNewbeeMallUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Controller
//@RequestMapping("/tbNewbeeMallUser")
public class TbNewbeeMallUserController {
    @Resource
    private TbNewbeeMallUserService tbNewbeeMallUserService;
    @ResponseBody
    @RequestMapping("/login")

    public Object qtLogin(String loginName,String password){

        Map<String,Object> map = new HashMap<String,Object>();
      QueryWrapper queryWrapper = new QueryWrapper<TbNewbeeMallUser>();
      queryWrapper.eq("loginName",loginName);
      List<TbNewbeeMallUser> list = tbNewbeeMallUserService.list(queryWrapper);
      if(list.size()>0){
          String md5Text = DigestUtils.md5Hex(password);
          System.out.println("加密后的密码"+md5Text);
          if(list.get(0).getPasswordMd5()==password.toString()){
              map.put("resultCode",200);
              System.out.println("登录成功！");
          }else{
              System.out.println("密码错误！");
          }
      }else{
          System.out.println("用户名不存在！");
      }
      return map;
  }
}

