package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.domain.CommonResult;
import com.atguigu.springcloud.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    @RequestMapping("/storage/decrease")
    public CommonResult decrease(Long productId,Integer count) {
        storageService.decrease(productId, count);
        return new CommonResult(200,"扣减库存成功!");
    }
}
