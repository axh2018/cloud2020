package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHtstrixService
{
    @Override
    public String paymentInfo_OK(Integer id)
    {
        return "PaymentFallbackService OK  fallback";
    }

    @Override
    public String paymentInfo_Timeout(Integer id)
    {
        return "PaymentFallbackService timeout fallback";
    }
}