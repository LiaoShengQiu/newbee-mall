package com.example.newbeemall.controller.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.newbeemall.config.AlipayConfig;
import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import com.example.newbeemall.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * 支付宝-手机网站支付.
 * <p>
 * 手机网站支付
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2018/6/11
 */
@Slf4j
@Controller
@RequestMapping("/alipay/web")
public class AlipayWebPayController {

    @Resource
    private AlipayClient alipayClient;

    @Resource
    public TbNewbeeMallOrderService orderService;

    @RequestMapping("/index")
    public String index(TbNewbeeMallOrder order, HttpServletRequest request){
        System.out.println("----------------alipay/index");
        TbNewbeeMallOrder orderByOrderNo = orderService.findOrderByOrderNo(order.getOrderNo());
        request.setAttribute("WIDout_trade_no",orderByOrderNo.getOrderNo());
        request.setAttribute("WIDtotal_amount",orderByOrderNo.getTotalPrice());
        request.setAttribute("WIDsubject","购物订单");
        return "alipay/index";
    }

    @RequestMapping("/zf")
    public void zf(String WIDout_trade_no, String WIDsubject, String WIDbody, HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        TbNewbeeMallOrder orderByOrderNo = orderService.findOrderByOrderNo(WIDout_trade_no);
        //获得初始化的AlipayClient
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(orderByOrderNo.getOrderNo());
        //付款金额，必填
        String total_amount = new String(orderByOrderNo.getTotalPrice().toString());
        //订单名称，必填
        String subject = new String(WIDsubject);
        //商品描述，可空
        String body = new String(WIDbody);

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        request.setAttribute("result",result);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(result);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 去支付
     *
     * 支付宝返回一个form表单，并自动提交，跳转到支付宝页面
     *
     * @param response
     * @throws Exception
     */
    @PostMapping("/alipage")
    public void gotoPayPage(HttpServletResponse response) throws AlipayApiException, IOException {
        // 订单模型
        String productCode="QUICK_WAP_WAY";
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(UUID.randomUUID().toString());
        model.setSubject("支付测试");
        model.setTotalAmount("0.01");
        model.setBody("支付测试，共0.01元");
        model.setTimeoutExpress("2m");
        model.setProductCode(productCode);

        AlipayTradeWapPayRequest wapPayRequest =new AlipayTradeWapPayRequest();
        wapPayRequest.setReturnUrl(AlipayConfig.return_url);
        wapPayRequest.setNotifyUrl(AlipayConfig.notify_url);
        wapPayRequest.setBizModel(model);

        // 调用SDK生成表单, 并直接将完整的表单html输出到页面
        String form = alipayClient.pageExecute(wapPayRequest).getBody();
        System.out.println(form);
        response.setContentType("text/html;charset=" + AlipayConfig.charset);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     * 支付宝页面跳转同步通知页面
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @RequestMapping("/returnUrl")
    public String returnUrl(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {
        response.setContentType("text/html;charset=" + AlipayConfig.charset);

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean verifyResult = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, "RSA2");
        if(verifyResult){
            //验证成功
            //请在这里加上商户的业务逻辑程序代码，如保存支付宝交易号
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            return "redirect:/paySuccess?orderNo="+out_trade_no;

        }else{
            return "wapPayFail";

        }
    }

    /**
     * 退款
     * @param id orderNo 商户订单号
     * @return
     */
    @PostMapping("/refund")
    @Transactional
    @ResponseBody
    public Object refund(@RequestBody int id) throws AlipayApiException {
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        TbNewbeeMallOrder byId = orderService.getById(id);
        ResultUtil resultUtil = new ResultUtil();
        //确定是已支付并且是支付宝
        if(byId.getPayStatus()==1 && byId.getPayType()==1){
            UpdateWrapper<TbNewbeeMallOrder> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("order_status",-1);
            updateWrapper.eq("order_",id);
            boolean b = orderService.update(updateWrapper);
            if(b){
                AlipayTradeRefundModel model=new AlipayTradeRefundModel();
                // 商户订单号
                model.setOutTradeNo(byId.getOrderNo());
                // 退款金额
                model.setRefundAmount(byId.getTotalPrice().toString());
                // 退款原因
                model.setRefundReason("无理由退货");
                // 退款订单号(同一个订单可以分多次部分退款，当分多次时必传)
//        model.setOutRequestNo(UUID.randomUUID().toString());
                alipayRequest.setBizModel(model);

                AlipayTradeRefundResponse alipayResponse = alipayClient.execute(alipayRequest);
                System.out.println(alipayResponse.getBody());
                if(alipayResponse.getFundChange().equals("Y")){
                    resultUtil.setMessage("退款成功");
                    resultUtil.setResultCode(200);
                } else {
                    resultUtil.setResultCode(500);
                    resultUtil.setMessage(alipayResponse.getSubMsg());
                    throw new AlipayApiException();
                }
            }
        } else {
            resultUtil.setResultCode(500);
            resultUtil.setMessage("出现异常");
        }
        return resultUtil;

    }

    /**
     * 退款查询
     * @param orderNo 商户订单号
     * @param refundOrderNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部订单号
     * @return
     * @throws AlipayApiException
     */
    @GetMapping("/refundQuery")
    @ResponseBody
    public String refundQuery(String orderNo, String refundOrderNo) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        AlipayTradeFastpayRefundQueryModel model=new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(orderNo);
        model.setOutRequestNo(refundOrderNo);
        alipayRequest.setBizModel(model);

        AlipayTradeFastpayRefundQueryResponse alipayResponse = alipayClient.execute(alipayRequest);
        System.out.println(alipayResponse.getBody());

        return alipayResponse.getBody();
    }

    /**
     * 关闭交易
     * @param orderNo
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/close")
    @ResponseBody
    public String close(String orderNo) throws AlipayApiException {
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model =new AlipayTradeCloseModel();
        model.setOutTradeNo(orderNo);
        alipayRequest.setBizModel(model);

        AlipayTradeCloseResponse alipayResponse= alipayClient.execute(alipayRequest);
        System.out.println(alipayResponse.getBody());

        return alipayResponse.getBody();
    }
}