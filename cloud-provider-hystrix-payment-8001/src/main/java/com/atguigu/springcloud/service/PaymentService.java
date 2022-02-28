package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Service
//默认通用兜底方法
@DefaultProperties(defaultFallback = "globalErrorHandler")
public class PaymentService
{

    /**
     * 正常方法
     * @param id
     * @return
     */
    @HystrixCommand
    public String paymentInfo(Integer id){
        return "线程池" + Thread.currentThread().getName() + "PaymentInfo-OK,id:" + id;
    }

    /**
     * 超时方法
     * @param id
     * @return
     */
    //超时时间设置为3秒，独享兜底方法
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfoTimeout(Integer id){
        //int a = 10;
        //随机休眠
        int sleepTime = new Random().nextInt(6);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池" + Thread.currentThread().getName() + "PaymentInfo-Timeout,id:" + id;
    }

    /**
     * 兜底方法
     * @param id
     * @return
     */
    public String paymentInfoTimeoutHandler(Integer id){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "系统繁忙，/PaymentInfo-Timeout-handler,id:" + id;
    }

    /**
     * 通用兜底方法
     * @param id
     * @return
     */
    public String globalErrorHandler(Integer id){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "系统繁忙，/PaymentInfo-Timeout-handler,id:" + id;
    }


    //====服务熔断

    /**
     * 在10秒窗口期中10次请求有6次是请求失败的,断路器将起作用
     *
     * @param id
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期/时间范文
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸  10秒内访问10次，失败率达到60%跳闸
    }
    )
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id不能是负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功,流水号:" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数,请稍后重试,o(╥﹏╥)o id:" + id;
    }
}