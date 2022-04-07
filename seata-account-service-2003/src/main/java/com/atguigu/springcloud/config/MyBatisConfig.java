package com.atguigu.springcloud.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.eiletxie.springcloud.alibaba.dao"})
public class MyBatisConfig {


}
