package com.huahua.qa.eureka.impl;

import com.huahua.qa.eureka.LabelEureka;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelEurekaImpl implements LabelEureka {

    @Override
    public Result findById(String id) {
        return new Result(StatusCode.ERROR,false,"熔断器启动了");
    }
}
