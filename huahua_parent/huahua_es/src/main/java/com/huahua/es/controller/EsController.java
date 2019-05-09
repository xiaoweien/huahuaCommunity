package com.huahua.es.controller;

import com.huahua.es.pojo.ArticleEs;
import com.huahua.es.service.EsService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/es")
@CrossOrigin
public class EsController {

    @Autowired private EsService esService;

    /**
     * 功能描述：条件查询
     * @Author LiHuaMing
     * @Date 21:27 2019/4/22
     * @Param [keywords, page, size]
     * @return huahua.common.Result
     */
    @RequestMapping(method = RequestMethod.GET,value = "/search/{keywords}/{page}/{size}")
    public Result searchArticle(@PathVariable("keywords")String keywords, @PathVariable("page")Integer page, @PathVariable("size")Integer size){
        Page<ArticleEs> pageList = esService.searchArticle(keywords, page, size);
        return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<ArticleEs>(pageList.getTotalElements(),pageList.getContent()));
    }
    /**
     * 功能描述：添加
     * @Author LiHuaMing
     * @Date 11:20 2019/4/22
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody ArticleEs articleEs){
        esService.add(articleEs);
        return new Result(StatusCode.SUCCESS,true,"添加成功");
    }
    /**
     * 功能描述：删除
     * @Author LiHuaMing
     * @Date 21:20 2019/4/22
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id")String id){
        esService.delete(id);
        return new Result(StatusCode.SUCCESS,true,"删除成功");
    }
    /**
     * 功能描述：查询
     * @Author LiHuaMing
     * @Date 21:27 2019/4/22
     * @Param [keywords, page, size]
     * @return huahua.common.Result
     */
    @RequestMapping(method = RequestMethod.GET,value = "/search/{page}/{size}")
    public Result findArticle(@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        Page<ArticleEs> pageList = esService.findArticle(page, size);
        return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<ArticleEs>(pageList.getTotalElements(),pageList.getContent()));
    }
    /**
     * 功能描述：修改
     * @Author LiHuaMing
     * @Date 21:28 2019/4/22
     * @Param 
     * @return 
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@PathVariable("id")String id,@RequestBody ArticleEs articleEs){
        esService.update(articleEs);
        return new Result(StatusCode.SUCCESS,true,"修改成功");
    }
}
