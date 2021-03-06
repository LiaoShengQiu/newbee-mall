package com.example.newbeemall.controller.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.example.newbeemall.config.AlipayConfig;
import com.example.newbeemall.entity.TbNewbeeMallOrder;
import com.example.newbeemall.service.TbNewbeeMallOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


    /**
     * 支付宝通用接口.
     * <p>
     * detailed description
     *
     * @author Mengday Zhang
     * @version 1.0
     * @since 2018/6/13
     */
    @Slf4j
    @RestController
    @RequestMapping("/alipay")
    public class AlipayController {
        @Resource
        private AlipayClient alipayClient;

       /* @Resource
        private AlipayTradeService alipayTradeService;*/

        /**
         * 支付异步通知
         * <p>
         * https://docs.open.alipay.com/194/103296
         */
        @RequestMapping("/notify")
        public String notify(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
            // 一定要验签，防止黑客篡改参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            StringBuilder notifyBuild = new StringBuilder("/****************************** alipay notify ******************************/\n");
            parameterMap.forEach((key, value) -> notifyBuild.append(key + "=" + value[0] + "\n"));
            log.info(notifyBuild.toString());

            // https://docs.open.alipay.com/54/106370
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            boolean flag = AlipaySignature.rsaCheckV1(params,
                    AlipayConfig.alipay_public_key,
                    AlipayConfig.charset,
                    AlipayConfig.sign_type);

            if (flag) {
                /**
                 * TODO 需要严格按照如下描述校验通知数据的正确性
                 *
                 * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
                 * 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
                 * 同时需要校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
                 *
                 * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
                 * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
                 * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
                 */

                //交易状态
                String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

                // TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
                // TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
                if (tradeStatus.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                    // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                } else if (tradeStatus.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                    // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。

                }

                return "success";
            }

            return "fail";
        }

        /*
         * 订单查询(最主要用于查询订单的支付状态)
         * @param orderNo 商户订单号
         * @return
         */
        @GetMapping("/query")
        public void query(String orderNo) {

            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            request.setBizContent("{" + "   \"out_trade_no\":\"" + orderNo
                    + "\"}");// 设置业务参数
            AlipayTradeQueryResponse response;
            try {
                response = alipayClient.execute(request);
                System.out.print(response.getBody());
                Map<String, String> restmap = new HashMap<String, String>();// 返回提交支付宝订单交易查询信息
                boolean flag = false; // 查询状态
                if (response.isSuccess()) {
                    // 调用成功，则处理业务逻辑
                    if ("10000".equals(response.getCode())) {
                        // 订单创建成功
                        flag = true;
                        restmap.put("order_no", response.getOutTradeNo());
                        restmap.put("trade_no", response.getTradeNo());
                        restmap.put("buyer_logon_id", response.getBuyerLogonId());
                        restmap.put("trade_status", response.getTradeStatus());
                    } else {

                    }
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
    }