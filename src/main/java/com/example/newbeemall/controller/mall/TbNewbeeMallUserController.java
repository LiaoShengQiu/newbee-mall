package com.example.newbeemall.controller.mall;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.service.TbNewbeeMallOrderItemService;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.service.TbNewbeeMallUserService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import com.example.newbeemall.utils.PhoneCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private TbNewbeeMallOrderItemService tbNewbeeMallOrderItemService;
    @Resource
    private TbNewbeeMallOrderService tbNewbeeMallOrderService;

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
                    request.getSession().setAttribute("newBeeMallUser",list.get(0));
                    System.out.println("登录成功！");
                }else{
                    msg = "密码错误！";
                }
            }else{
                msg = "用户名不存在！";
            }
        }else{
            msg = "图片验证码不对！";
        }
      map.put("msgs",msg);
      return map;
  }



    /**
     * 注册
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

    /**
     * 个人中心
     */
    @RequestMapping("/personal")
    public String mallUser_show(){
        return "/mall/personal";
    }

    /**
     * 修改个人信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/personal/updateInfo")
    @ResponseBody
    public Object upPersonall(@RequestBody Map<String,Object> map,HttpServletRequest request){
        System.out.println(map.get("userId")+"/personal/updateInfo--address"+map.get("address"));
        TbNewbeeMallUser tbNewbeeMallUser = new TbNewbeeMallUser();
        tbNewbeeMallUser.setAddress(map.get("address").toString());
        tbNewbeeMallUser.setUserId(Long.parseLong(map.get("userId").toString()));
        tbNewbeeMallUser.setNickName(map.get("nickName").toString());
        tbNewbeeMallUser.setIntroduceSign(map.get("introduceSign").toString());
        boolean b = tbNewbeeMallUserService.saveOrUpdate(tbNewbeeMallUser);
        QueryWrapper<TbNewbeeMallUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",Long.parseLong(map.get("userId").toString()));
        if (b){
            map.put("resultCode",200);
            List<TbNewbeeMallUser> list = tbNewbeeMallUserService.list(queryWrapper);
            request.getSession().setAttribute("newBeeMallUser",list.get(0));  //刷新会话
        }
        return map;
    }

    /**
     * 跳转到我的订单
     * @return
     */
    @RequestMapping("/orders")
    public String orders_Show(HttpServletRequest request, Model model,@RequestParam Map<String,Object> map){
        Long userId = null;
      if (request.getSession().getAttribute("userId") != null){
          userId =  Long.parseLong(request.getSession().getAttribute("userId").toString());
      }

        System.out.println("userId===================="+userId);
        if (StringUtils.isEmpty(map.get("page"))) {
            map.put("page", 1);
        }
        map.put("limit", 3);
        map.put("userId",userId);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(map);

        PageResult pageResult = tbNewbeeMallOrderService.myordersItems_list(userId,pageUtil);
     request.setAttribute("orderPageResult",pageResult);
        return "mall/my-orders";
  }

}

