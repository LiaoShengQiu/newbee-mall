package com.example.newbeemall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication   //它会默认启动很多底层支持的类和xml文件
@EnableScheduling  //开启定时任务注解
@MapperScan("com.example.newbeemall.mapper")
public class NewbeeMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewbeeMallApplication.class, args);
    }

}
