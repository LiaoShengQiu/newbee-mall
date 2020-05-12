package com.example.newbeemall.controller.mall;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallOrderItem;
import com.example.newbeemall.entity.TbNewbeeMallShoppingCartItem;
import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.entity.TbSeckillGoods;
import com.example.newbeemall.mapper.TbNewbeeMallShoppingCartItemMapper;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.service.TbSeckillOrderService;
import com.example.newbeemall.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ShopCatController {
    @Resource
    private TbNewbeeMallShoppingCartItemService shopCatService;
    @Resource
    private TbNewbeeMallShoppingCartItemMapper tbNewbeeMallShoppingCartItemMapper;
    @Resource
    private TbSeckillOrderService tbSeckillOrderService;
    @GetMapping("/shop-cart")
    public Object toCart(HttpServletRequest request,HttpSession session) {
        System.out.println("GetMapping(/shop-cart)");
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) session.getAttribute("newBeeMallUser");
        List<TbNewbeeMallOrderItem> cartByUserId = shopCatService.getCartByUserId(newBeeMallUser.getUserId());
        System.out.println(cartByUserId.toString());
        int itemsTotal = 0;
        int priceTotal = 0;
        for (TbNewbeeMallOrderItem item:cartByUserId) {
            itemsTotal+=item.getGoodsCount();
            if(item.getIsDeleted() == 0){
                priceTotal+=item.getGoodsCount()*item.getSellingPrice();
            }

            if (item.getIsMiaos() == 1){
                Date createTime = item.getCreateTime();
                Date updateTime = item.getUpdateTime(); //过期时间
                Date date = new Date();  //当前时间
                //8个小时的时间差
                long shicha = 8*60*60*1000;  //8个小时的时差
                System.out.println("过期时间和创建时间"+updateTime+"====================="+createTime+"当前时间" +date);
                long time = updateTime.getTime() - createTime.getTime();
                System.out.println("========"+time);
                if (item.getIsMiaos() == 1){
                    item.setMsg("限时秒杀");
                }
                long guoqi = updateTime.getTime()+2*60*60*1000;
                System.out.println(new Date()+"====当前时间搓"+new Date().getTime());
                System.out.println(updateTime+"====过期时间搓"+guoqi);

                if(guoqi < new Date().getTime()){
                    TbNewbeeMallShoppingCartItem tbNewbeeMallShoppingCartItem =new TbNewbeeMallShoppingCartItem();
                    tbNewbeeMallShoppingCartItem.setCartItemId(item.getCartItemId());
                    tbNewbeeMallShoppingCartItem.setIsDeleted(-1); //-1为过期
                    tbNewbeeMallShoppingCartItemMapper.updateById(tbNewbeeMallShoppingCartItem);
                    item.setMsg("该商品已过期！请重新添加购物车！");
                    request.setAttribute("isDelete",-1);
                }
            }
            if (item.getIsDeleted() == -1){
                item.setMsg("该商品已过期！请重新添加购物车！");
            }


        }
        System.out.println(cartByUserId.toString());
        request.setAttribute("itemsTotal",itemsTotal);
        request.setAttribute("priceTotal",priceTotal);

        request.setAttribute("myShoppingCartItems", cartByUserId);
        return "mall/cart";
    }

    @PutMapping("/shop-cart")
    @ResponseBody
    public Object updateShopCart(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session){
        System.out.println("PutMapping(shop-cart)");
        ResultUtil resultUtil = new ResultUtil(shopCatService.update(map));
        return resultUtil;
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Object addShopCart(@RequestBody TbNewbeeMallShoppingCartItem cartItem,HttpServletRequest request,HttpSession session){
        int isDeleted = 0;
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) session.getAttribute("newBeeMallUser");
        if(newBeeMallUser == null){
            ResultUtil resultUtil = new ResultUtil(newBeeMallUser);
            resultUtil.setMessage("请先登录！！！");
            System.out.println("请先登录");
            return resultUtil;
        }
        cartItem.setUserId(newBeeMallUser.getUserId());
        cartItem.setIsDeleted(isDeleted);
        TbNewbeeMallShoppingCartItem itemByUserIdByGoodsId = shopCatService.findItemByUserIdByGoodsId(newBeeMallUser.getUserId(), cartItem.getGoodsId());
        ResultUtil resultUtil = new ResultUtil();
        if(itemByUserIdByGoodsId!= null){
            Integer goodsCount = itemByUserIdByGoodsId.getGoodsCount();
            if(goodsCount <5){
                itemByUserIdByGoodsId.setGoodsCount(goodsCount+1);
                resultUtil.setResultCode(tbNewbeeMallShoppingCartItemMapper.updateById(itemByUserIdByGoodsId)>0?200:500);
            } else {
                resultUtil.setResultCode(500);
                resultUtil.setMessage("限购5件");
            }
        } else {
            resultUtil.setResultCode(shopCatService.saveCart(cartItem)?200:500);
            int count = shopCatService.getCartCountByUserId(newBeeMallUser.getUserId());
            newBeeMallUser.setShopCartItemCount(count);
        }
        session.setAttribute("newBeeMallUser", newBeeMallUser);
        return resultUtil;
    }



    /**
     * 删除购物车商品
     * @param id
     * @param request
     * @param session
     * @return
     */
    @DeleteMapping("/shop-cart/{id}")
    @ResponseBody
    public Object deleteShopCart(@PathVariable("id") Long id,HttpServletRequest request,HttpSession session){
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) session.getAttribute("newBeeMallUser");
        QueryWrapper<TbNewbeeMallShoppingCartItem> queryWrapper = new QueryWrapper<>();
        List<TbNewbeeMallShoppingCartItem> list = shopCatService.list(queryWrapper);

        long uid = newBeeMallUser.getUserId();
        int i = tbSeckillOrderService.deleteSeckill(uid, id);  //删除秒杀成功标的数据

        ResultUtil resultUtil = new ResultUtil(shopCatService.removeById(id));
        int count = shopCatService.getCartCountByUserId(newBeeMallUser.getUserId());
        newBeeMallUser.setShopCartItemCount(count);
        session.setAttribute("newBeeMallUser",newBeeMallUser);
        return resultUtil;
    }

    @GetMapping("/shop-cart/settle")
    public Object settle(HttpServletRequest request,HttpSession session){
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) session.getAttribute("newBeeMallUser");
        List<TbNewbeeMallOrderItem> cartByUserId = shopCatService.getCartByUserId2(newBeeMallUser.getUserId());
        int itemsTotal = 0;
        int priceTotal = 0;
        for (TbNewbeeMallOrderItem item:cartByUserId) {
            itemsTotal+=item.getGoodsCount();
            priceTotal+=item.getGoodsCount()*item.getSellingPrice();
        }
        request.setAttribute("itemsTotal",itemsTotal);
        request.setAttribute("priceTotal",priceTotal);
        request.setAttribute("myShoppingCartItems",cartByUserId);
        return "mall/order-settle";

    }
}
