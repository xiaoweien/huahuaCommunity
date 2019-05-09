package com.huahua.qa.eureka;

import com.huahua.qa.eureka.impl.LabelEurekaImpl;
import huahua.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//用于指定从哪个服务中调用功能，注意：里面的名称与被调用的服务名保持一致，并且不能包含下划线。
@FeignClient(value = "huahua-base",fallback = LabelEurekaImpl.class)
public interface LabelEureka {
    //用于对被调用的微服务进行地址映射。注意：@PathVariable注解一定要指定参数名称，否则出错。
    @RequestMapping(value = "/label/{id}",method = RequestMethod.GET)//value要写全路径
    Result findById(@PathVariable("id") String id);
}
