package com.example.newbeemall.controller;

import com.example.newbeemall.mapper.utils.VerifiCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 生成验证码的Controller
 */
@Controller
public class VerificodeController {

    @RequestMapping("/Verificode")
     public void Verificode(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        System.out.println("图片验证码================");
             /*
             1.生成验证码
             2.把验证码上的文本存在session中
             3.把验证码图片发送给客户端
             */
            VerifiCode v = new VerifiCode();     //用我们的验证码类，生成验证码类对象
            BufferedImage image = v.getImage();  //获取验证码
            request.getSession().setAttribute("text", v.getText()); //将验证码的文本存在session中
            v.output(image, response.getOutputStream());//将验证码图片响应给客户端

    }

}
