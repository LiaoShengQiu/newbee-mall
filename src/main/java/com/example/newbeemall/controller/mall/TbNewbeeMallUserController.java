package com.example.newbeemall.controller.mall;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.service.TbNewbeeMallUserService;
import com.example.newbeemall.utils.PhoneCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private TbNewbeeMallShoppingCartItemService cartItemService;

    @GetMapping({"/login", "login.html"})
    public String login(){
        System.out.println("/templates/mall/login");
        return "/mall/login";
    }

    @RequestMapping("/index")
    public String index(){
        return "/mall/index";
    }


    /**
     *
     * 短信登录
     * @return
     */
    @RequestMapping("/dlogin")
    public String index2(){
        return "/mall/duanxin.html";
    }

    @RequestMapping("/huoqud")
    @ResponseBody
    public  Object huoqu(String loginName,String time,HttpServletRequest request){
        System.out.println("获取短信验证码"+loginName);
        int seconds = Integer.parseInt(time)+1;
        try {
            PhoneCode.getPhonemsg(loginName,request);

            System.err.println("倒计时" + seconds + "秒,倒计时开始:");
            int i = seconds;
            while (i > 0) {
                System.err.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
                i--;
            }
         //   System.err.println(i);
            if(i<1){
                request.getSession().removeAttribute("code");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }
    @RequestMapping("/duanlogin")
    @ResponseBody
    public Object duaxLogin(String loginName,String yanz,String map,HttpServletRequest request){

        String code = "";

if(request.getSession().getAttribute("code")!=null){
    code = request.getSession().getAttribute("code").toString();
}
        System.out.println(code+"短信验证"+yanz);
      if(yanz.equals(code)){
          System.out.println("对了========================================");
          map="200";

      }else {
          System.out.println("请正确输入短信校验码！");
          map="请正确输入短信校验码！";
      }
        return map;
    }
    @RequestMapping("/dologin")
    @ResponseBody
    public Object qtLogin(String loginName, String password, String params, String verfyCode, HttpServletRequest request, ModelMap model){
        System.out.println("dologin===================="+loginName+"mima"+password);
        String tu = request.getSession().getAttribute("text").toString();
        System.out.println(verfyCode+"生成的图片校验码"+tu);
        Map<String,Object> map = new HashMap<String,Object>();
        String msg = "";
        if(tu.equalsIgnoreCase(verfyCode)){   //忽略大小写判断图片验证码
            QueryWrapper queryWrapper = new QueryWrapper<TbNewbeeMallUser>();
            queryWrapper.eq("login_name",loginName);
            List<TbNewbeeMallUser> list = tbNewbeeMallUserService.list(queryWrapper);
            if(list.size()>0){
                String md5Text = DigestUtils.md5Hex(password);
                System.out.println("加密后的密码"+md5Text);
                System.out.println("数据库的密码"+list.get(0).getPasswordMd5());
                if(list.get(0).getPasswordMd5().equals(md5Text)){
                    map.put("resultCode",200);
                    int count = cartItemService.getCartCountByUserId(list.get(0).getUserId());
                    list.get(0).setShopCartItemCount(count);
                    request.getSession().setAttribute("userId",list.get(0).getUserId());
                    request.getSession().setAttribute("userId",list.get(0).getUserId());
                    request.getSession().setAttribute("newBeeMallUser",list.get(0));
                    System.out.println("登录成功！");
                }else{
                    System.out.println("密码错误！");
                    msg = "密码错误！";
                }
            }else{
                System.out.println("用户名不存在！");
                msg = "用户名不存在！";
            }
        }else{
            System.out.println("图片验证码不对！");
            msg = "图片验证码不对！";
        }
      map.put("msgs",msg);
      return map;
  }



    /**
     * 注册3
     * @return
     */
    @GetMapping({"/register", "register.html"})
    public String regist(){
        return "/mall/register";
    }

  @RequestMapping("/register")
  @ResponseBody
    public Object register(String loginName, String password,String verifyCode,HttpServletRequest request){
      String tu = request.getSession().getAttribute("text").toString();
      System.out.println("password"+password+verifyCode+"生成的图片校验码"+tu);
      Map<String,Object> map = new HashMap<String,Object>();
      QueryWrapper<TbNewbeeMallUser> queryWrapper = new QueryWrapper<TbNewbeeMallUser>();
      queryWrapper.eq("login_name",loginName);
      List<TbNewbeeMallUser> list = tbNewbeeMallUserService.list(queryWrapper);
      if(list.size()>0){
          map.put("msgs","该账户已被注册!");
      }else{
          if(tu.equalsIgnoreCase(verifyCode)){
              TbNewbeeMallUser tbNewbeeMallUser = new TbNewbeeMallUser();
              tbNewbeeMallUser.setLoginName(loginName);
              String md5Text = DigestUtils.md5Hex(password);
              tbNewbeeMallUser.setPasswordMd5(md5Text);
              boolean isok = tbNewbeeMallUserService.saveOrUpdate(tbNewbeeMallUser);
              if(isok == true){
                  map.put("resultCode",200);
              }
          }else{
              map.put("msgs","图片验证码错误！");
          }
      }
        return map;
  }
}

