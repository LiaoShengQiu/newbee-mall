package com.example.newbeemall.controller.mall;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.config.MyRabbitMQConfig;
import com.example.newbeemall.entity.*;
import com.example.newbeemall.mapper.*;
import com.example.newbeemall.service.RedisService;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import com.example.newbeemall.service.TbNewbeeMallShoppingCartItemService;
import com.example.newbeemall.service.TbSeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@Slf4j
public class miaoshaController {



    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;
    @Resource
    private TbNewbeeMallGoodsInfoMapper tbNewbeeMallGoodsInfoMapper;
    @Resource
    private TbSeckillGoodsMapper tbSeckillGoodsMapper;
    @Resource
    private TbNewbeeMallOrderItemMapper tbNewbeeMallOrderItemMapper;
    @Resource
    private TbNewbeeMallOrderMapper tbNewbeeMallOrderMapper;
    @Resource
    private TbSeckillOrderService tb_seckill_orderService;
    @Resource
    private TbSeckillOrderMapper tbSeckillOrderMapper;
    @Resource
    private TbNewbeeMallGoodsInfoService goodsService;
    @Resource
    private TbNewbeeMallShoppingCartItemService tbNewbeeMallShoppingCartItemService;


    @RequestMapping("/goods/miaosha/{goodsId}")
    public String toDetailMiaosha(@PathVariable("goodsId") Long goodsId, HttpServletRequest request){
        if(goodsId < 1){
            return "error/error_5xx";
        }
        TbNewbeeMallGoodsInfo goods = goodsService.getById(goodsId);
        System.out.println(goods.toString());
        request.setAttribute("stockName",goods.getGoodsId().toString());
        //根据key获取值
        if(redisService.isExist(goods.getGoodsName()) == null){
            //存入redis
            redisService.put(goods.getGoodsName(), goods.getStockNum(),10);
        }
        redisService.put("username", 0,1);
        QueryWrapper<TbSeckillGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id",goodsId);
        List<TbSeckillGoods> tbSeckillGoods = tbSeckillGoodsMapper.selectList(queryWrapper);
        System.out.println("tbSeckillGoods.get(0)"+tbSeckillGoods.get(0));
        request.setAttribute("goodsDetail",goods);
        request.setAttribute("tbSGoods",tbSeckillGoods.get(0));
        System.out.println("mall/miaosha=============="+tbSeckillGoods.get(0).getEndTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(tbSeckillGoods.get(0).getEndTime());
        request.getSession().setAttribute("tbNewbeeMallGoodsInfo",goods);
        System.out.println("tbSeckillGoods"+tbSeckillGoods.toString());
        request.getSession().setAttribute("tbSeckillGoods",tbSeckillGoods.get(0));   //这里主要是传结束时间
        request.setAttribute("shijian",format);
        System.out.println(format+"format"+tbSeckillGoods.get(0).getEndTime()+"结束时间"+tbSeckillGoods.get(0).getEndTime().getTime());
       return "mall/miaos";
    }

    /**
     * 显示秒杀的所有商品
     * @return
     */
    @RequestMapping("/miaosha")
    public String miaosha(HttpServletRequest request, @RequestParam Map<String,Object> map){
     /*   这里模拟显示商品  只插入了几条数据
         sql语句 （SELECT *FROM tb_newbee_mall_goods_info WHERE goods_id IN (10037,10038)）
        */
        if (StringUtils.isEmpty(map.get("page"))) {
            map.put("page", 1);
        }
        int page = Integer.parseInt(map.get("page").toString());
        int end = 10;  //页大小
        int start = (page-1)*end;
        int count = tbNewbeeMallGoodsInfoMapper.count(start,end);

        System.out.println("向上取整"+(int)count/end+1);
        long sum = count%end == 0 ? count/end : count/end+1;
        List<TbNewbeeMallGoodsInfo> tbNewbeeMallGoodsInfos = tbNewbeeMallGoodsInfoMapper.listGoods(start,end);
        request.setAttribute("pageResult",tbNewbeeMallGoodsInfos);
        System.out.println("总记录数========"+count+"总页数"+sum);
        request.setAttribute("page",page);
        request.setAttribute("count",sum);
        return "mall/miaoshadetail.html";
    }

    @RequestMapping("/miaoshafenye")
    @ResponseBody
    public Object miaoshafy(HttpServletRequest request, @RequestParam Map<String,Object> map){

        return map;
    }




    /**
     * 使用redis+消息队列进行秒杀实现
     *
     * @param username
     * @param stockName
     * @return
     */
    @RequestMapping("/sec")
    @ResponseBody
    @Transactional
   // public Object sec(@RequestParam(value = "username") String username, @RequestParam(value = "stockName") String stockName, HttpServletRequest request,@RequestParam("goodsId")Integer goodsId,@RequestParam(value = "sgId")Integer sgId,@RequestParam(value = "orderId") Long orderId,HttpSession session) {
        public Object seccc(String username, String stockName,@RequestBody TbNewbeeMallShoppingCartItem cartItem,HttpServletRequest request,Long goodsId,Long orderId){
 //       redisService.put("username", 7999,20);
        int resultCode = 0;
        //调用redis给相应商品库存量加一
        Long count = redisService.addBy("username");

      Map<String,Object> map = new HashMap<String,Object>();
        if(count > 8000){
            resultCode = -100;
            map.put("resultCode",resultCode);
            return  map;
        }
        long miaoId = cartItem.getGoodsId();
        TbSeckillGoods tbSeckillGoods1 = tbSeckillGoodsMapper.selectById(miaoId);
        //查询商品表
        TbNewbeeMallGoodsInfo tbNewbeeMallGoodsInfo = (TbNewbeeMallGoodsInfo) request.getSession().getAttribute("tbNewbeeMallGoodsInfo");
        System.out.println(tbNewbeeMallGoodsInfo.toString());
        Integer num = tbNewbeeMallGoodsInfo.getStockNum();
        long sgId =  cartItem.getGoodsId(); //这里其实传过来的是秒杀表的id
        TbSeckillGoods tbSeckillGoods = (TbSeckillGoods) request.getSession().getAttribute("tbSeckillGoods");
        goodsId = (long)tbSeckillGoods.getGoodsId();
        if (orderId == null){
            Integer itemId = tbSeckillGoods.getItemId();
            TbNewbeeMallOrderItem tbNewbeeMallOrderItem = tbNewbeeMallOrderItemMapper.selectById(itemId);
            orderId = tbNewbeeMallOrderItem.getOrderId();
        }
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) request.getSession().getAttribute("newBeeMallUser");
       long uid = newBeeMallUser.getUserId();


       if(username == null){
           username = newBeeMallUser.getNickName();
       }
        int num2 = 0;
       System.out.println("========================"+goodsId);
        goodsId = (long) tbSeckillGoods.getGoodsId();
         if(tbNewbeeMallGoodsInfo != null){
               stockName = tbNewbeeMallGoodsInfo.getGoodsName();   //商品名称
               num2 = tbNewbeeMallGoodsInfo.getStockNum(); //商品数量
           }

      /*    redisService.put(stockName, num, 20);*/
        log.info("参加秒杀的用户是：{}，秒杀的商品是：{}", username, stockName);
        String message = null;
        /**
         * 1.创建订单之前判断用户是否已经参加秒杀过该商品  一个用户只可以秒杀一次！
         * 2.说明该商品的库存量有剩余，可以进行下订单操作
         */
        QueryWrapper queryWrapper =  new QueryWrapper<tb_seckill_order>();
        queryWrapper.eq("seckill_id",sgId);
        queryWrapper.eq("user_id",uid);
        List<tb_seckill_order> list =  tbSeckillOrderMapper.selectList(queryWrapper);

        if(list.size() == 0){
        //调用redis给相应商品库存量减一
        Long decrByResult = redisService.decrBy(stockName);
        System.out.println("库存========="+decrByResult);
          if (decrByResult >= 0) {
                log.info("用户：{}秒杀该商品：{}库存还有{}，可以进行下订单操作", username, stockName, decrByResult);
                double mouney = tbSeckillGoods.getNum()*tbSeckillGoods.getCostPrice();  //支付金额
                Date nowDate = new Date();
                String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                Random random=new Random();
                StringBuffer sb=new StringBuffer();
                for(int i=0;i<10;i++){
                    int number=random.nextInt(62);
                    sb.append(str.charAt(number));
                }
                String liusui =  sb.toString();    //流水号
                Integer itemId = tbSeckillGoods.getItemId();
                if(orderId == null){
                    TbNewbeeMallOrderItem tbNewbeeMallOrderItem = tbNewbeeMallOrderItemMapper.selectById(itemId);
                    orderId = tbNewbeeMallOrderItem.getOrderId();
                }
                //查询订单表
                TbNewbeeMallOrder tbNewbeeMallOrder = tbNewbeeMallOrderMapper.selectById(orderId);
                System.out.println("tbNewbeeMallOrder====================="+tbNewbeeMallOrder);
                //发消息给订单消息队列，创建订单
              rabbitTemplate.convertAndSend(MyRabbitMQConfig.STORY_EXCHANGE, MyRabbitMQConfig.STORY_ROUTING_KEY, stockName);
                tb_seckill_order tb_seckill_order = new tb_seckill_order();
                tb_seckill_order.setSeckillId((int) sgId);
                tb_seckill_order.setMoney(mouney);
                tb_seckill_order.setUserId(newBeeMallUser.getUserId());
                tb_seckill_order.setCreateTime(nowDate);
                tb_seckill_order.setReceiverAdd(tbNewbeeMallOrder.getUserAddress());  //收获人地址
                tb_seckill_order.setReceiverMob(tbNewbeeMallOrder.getUserPhone());  //收货人电话
                tb_seckill_order.setReceiver(tbNewbeeMallOrder.getUserName());
                //状态 0失败 1成功
                tb_seckill_order.setStatus("1");
                tb_seckill_order.setTransactionId(sgId+liusui);
                boolean save = tb_seckill_orderService.save(tb_seckill_order);
                System.out.println("添加成功？？？"+save);
                resultCode = 200;
                message = "用户" + username + "秒杀" + stockName + "成功";
                TbNewbeeMallShoppingCartItem cartItem2 = new TbNewbeeMallShoppingCartItem();
              shuaxin(cartItem2,goodsId,1,request);   //加入购物车
            }else {
              /**
               * 说明该商品的库存量没有剩余，直接返回秒杀失败的消息给用户
               */
              log.info("用户：{}秒杀时商品的库存量没有剩余,秒杀结束", username);
              message = "用户："+ username + "商品的库存量没有剩余,秒杀结束";
              map.put("massag","商品的库存量没有剩余,秒杀结束");
              resultCode = 0;
          }
        }else {
            message = "您已经参与过该活动了！";
            resultCode = 1;
            map.put("massag",message);
            request.setAttribute("mesg",message);
        }
        log.info(message);
        map.put("resultCode",resultCode);
        return map;
    }


    /**
     * 测试
     * @param username
     * @param stockName
     * @param gid
     * @return
     */
    @RequestMapping("/seccc")
    @ResponseBody
    @Transactional
    public String secu(@RequestParam(value = "username") String username, String stockName, @RequestParam(value = "gid") int gid) throws Exception{

        //发消息给库存消息队列，将库存数据加一
     //   rabbitTemplate.convertAndSend(MyRabbitMQConfig.USER_EXCHANGE, MyRabbitMQConfig.USER_EN_KEY, "username");
        TbNewbeeMallGoodsInfo tbNewbeeMallGoodsInfo = tbNewbeeMallGoodsInfoMapper.selectById(gid);
        //      TbNewbeeMallGoodsInfo tbNewbeeMallGoodsInfo = goodsService.findByid(gid); //查询商品表
 //       log.info("是否存在"+redisService.isExist(tbNewbeeMallGoodsInfo.getGoodsName()));
        stockName = tbNewbeeMallGoodsInfo.getGoodsName();
        //根据key获取值
        if(redisService.isExist(tbNewbeeMallGoodsInfo.getGoodsName()) == null){
            //存入redis
            redisService.put(tbNewbeeMallGoodsInfo.getGoodsName(), tbNewbeeMallGoodsInfo.getStockNum());
        }

       log.info("参加秒杀的用户是：{}，秒杀的商品是：{}", username, stockName);
        String message = null;
        //调用redis给相应访问量加一
        Long count = redisService.addBy("username");
        //调用redis给相应商品库存量减一
        Long decrByResult = redisService.decrBy(stockName);
        if(count > 8000){
            message = "当前参与的人员较多请稍后重试！";
            //       rabbitAdmin.removeBinding(MyRabbitMQConfig.noargs);  //解除绑定
        }
        if (decrByResult >= 0) {
            /**
             * 说明该商品的库存量有剩余，可以进行下订单操作
             */
            log.info("用户：{}秒杀该商品：{}库存还有{}，可以进行下订单操作", username, stockName, decrByResult);
            //发消息给库存消息队列，将库存数据减一
            rabbitTemplate.convertAndSend(MyRabbitMQConfig.STORY_EXCHANGE, MyRabbitMQConfig.STORY_ROUTING_KEY, stockName);

            //发消息给订单消息队列，创建订单

            message = "用户" + username + "秒杀" + stockName + "成功"+"库存还有"+decrByResult;
        } else {
            /**
             * 说明该商品的库存量没有剩余，直接返回秒杀失败的消息给用户
             */
      //      log.info("用户：{}秒杀时商品的库存量没有剩余,秒杀结束", username);
            message = "用户："+ username + "商品的库存量没有剩余,秒杀结束";
        }
//        log.info(message);

        log.info(message);

        return message;
    }




    /**
     * 秒杀那边过来的  加入购物车
     * @param cartItem
     * @param
     * @return
     */
    public boolean addShopCart2(TbNewbeeMallShoppingCartItem cartItem,long goodsId,int goodsCount, HttpServletRequest request) {

        TbSeckillGoods tbSeckillGoods = (TbSeckillGoods) request.getSession().getAttribute("tbSeckillGoods");
        long guoqi = 2*60*60*1000;    //两个小时自动过期
        long shicha = 8*60*60*1000;  //8个小时的时差
        long endTime = tbSeckillGoods.getEndTime().getTime();  //获取时间戳
        System.out.println(tbSeckillGoods.getEndTime()+"活动结束时间"+endTime+"\taaaa"+tbSeckillGoods.getEndTime());
        Date now = new Date();
        System.out.println(now.getTime()+"当前时间"+now);
 //       Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long time = now.getTime()+guoqi+shicha;  //过期时间的时间戳
        long to = 0;
        if(endTime - now.getTime() > 0){
             to = endTime - time;
        }
        System.out.println(to+"/shop-cart"+endTime);
        int isDeleted = 0;   //0显示 1删除
       /* if (to <= 0){
            isDeleted = 1;  //删除  过期
        }*/

       //结束时间
        Date time2 = new Date(time);
  //      LocalDateTime time2 =LocalDateTime.ofEpochSecond(time/1000,0,ZoneOffset.ofHours(8));
        System.out.println("结束时间======="+time2);
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser)  request.getSession().getAttribute("newBeeMallUser");
        cartItem.setUserId(newBeeMallUser.getUserId());
        cartItem.setIsDeleted(isDeleted);
        cartItem.setGoodsId(goodsId);
        cartItem.setGoodsCount(goodsCount);
        cartItem.setUpdateTime(time2);
        cartItem.setIsMiaos(1);  // 1表示是秒杀
        boolean isok =false;
        try{
            isok = tbNewbeeMallShoppingCartItemService.save(cartItem);
        }catch (Exception e){
            e.printStackTrace();
        }

//        ResultUtil resultUtil = new ResultUtil(tbNewbeeMallShoppingCartItemService.saveCart(cartItem));
        return isok;
    }

    public void shuaxin(TbNewbeeMallShoppingCartItem cartItem,long goodsId,int goodsCount, HttpServletRequest request){
        System.out.println("刷新购物车===========");
        boolean o = addShopCart2(cartItem, goodsId, 1, request);
        TbNewbeeMallUser newBeeMallUser = (TbNewbeeMallUser) request.getSession().getAttribute("newBeeMallUser");
        int count = tbNewbeeMallShoppingCartItemService.getCartCountByUserId(newBeeMallUser.getUserId());
        newBeeMallUser.setShopCartItemCount(count);
        request.getSession().setAttribute("newBeeMallUser",newBeeMallUser);
    }
}
