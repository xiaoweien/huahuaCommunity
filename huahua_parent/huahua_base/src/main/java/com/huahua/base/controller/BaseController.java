package com.huahua.base.controller;

import com.huahua.base.dtojpa.Label;
import com.huahua.base.service.BaseService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/label")
@RefreshScope//刷新自定义的配置
public class BaseController{
    @Autowired private BaseService baseService;

    @Value("${ip}")
    private String ip;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        //label = null;
        baseService.add(label);
        return new Result(StatusCode.SUCCESS,true,"添加成功");
    }
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        System.out.println("自定义的配置ip为："+ip);
        return new Result(StatusCode.SUCCESS,true,"查询成功",baseService.findAll());
    }
    @RequestMapping(method = RequestMethod.GET,value = "/toplist")
    public Result toplist(){
        return new Result(StatusCode.SUCCESS,true,"查询成功",baseService.toplist("1"));
    }
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public Result list(){
        return new Result(StatusCode.SUCCESS,true,"查询成功",baseService.list("2"));
    }
    //PathVariable  获取地址上的值
    @RequestMapping(method = RequestMethod.GET,value = "/{labelId}")
    public Result findById(@PathVariable("labelId")String labelId){
        System.out.println("----------我进入服务了----------");
        return new Result(StatusCode.SUCCESS,true,"查询成功",baseService.findById(labelId));
    }
    @RequestMapping(method = RequestMethod.PUT,value = "/{labelId}")
    public Result update(@RequestBody Label label,@PathVariable("labelId")String labelId){
        label.setId(labelId);
        baseService.add(label);
        return new Result(StatusCode.SUCCESS,true,"修改成功");
    }
    @RequestMapping(method = RequestMethod.DELETE,value = "/{labelId}")
    public Result delete(@PathVariable("labelId")String labelId){
        baseService.delete(labelId);
        return new Result(StatusCode.SUCCESS,true,"删除成功");
    }
    @RequestMapping(method = RequestMethod.POST,value = "/search/{page}/{size}")
    public Result search(@RequestBody Label label,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        Page<Label> search = baseService.search(label, page, size);
        return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<>(search.getTotalElements(),search.getContent()));
    }
}
