package com.huahua.spit.controller;

import com.huahua.spit.pojo.Spit;
import com.huahua.spit.service.SpitService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {
    @Autowired private RedisTemplate redisTemplate;
    @Autowired private SpitService spitService;
    @Autowired private HttpServletRequest httpServletRequest;
    /**
     * 功能描述：查询全部
     * @Author LiHuaMing
     * @Date 19:34 2019/4/17
     * @Param []
     * @return huahua.common.Result
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        Claims claims = (Claims) httpServletRequest.getAttribute("admin_claims");
        if (null == claims){
            return new Result(StatusCode.NOT_POWER,false,"权限不足");
        }
        return new Result(StatusCode.SUCCESS,true,"查询成功",spitService.findAll());
    }
    /**
     * 功能描述：根据id查询
     * @Author LiHuaMing
     * @Date 19:34 2019/4/17
     * @Param [id]
     * @return huahua.common.Result
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findById(@PathVariable("id")String id){
        return new Result(StatusCode.SUCCESS,true,"查询成功",spitService.findById(id));
    }
    /**
     * 功能描述：添加
     * @Author LiHuaMing
     * @Date 19:34 2019/4/17
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spit.setPublishtime(new Date());
        spitService.add(spit);
        return new Result(StatusCode.SUCCESS,true,"添加成功");
    }
    /**
     * 功能描述：修改
     * @Author LiHuaMing
     * @Date 19:34 2019/4/17
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{id}")
    public Result update(@RequestBody Spit spit,@PathVariable("id")String id){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(StatusCode.SUCCESS,true,"修改成功");
    }
    /**
     * 功能描述：删除
     * @Author LiHuaMing
     * @Date 19:35 2019/4/17
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/{id}")
    public Result delete(@PathVariable("id")String id){
        spitService.delete(id);
        return new Result(StatusCode.SUCCESS,true,"删除成功");
    }
    /**
     * 功能描述：根据上级id查询吐槽数据(分页)
     * @Author LiHuaMing
     * @Date 19:47 2019/4/17
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/comment/{parentid}/{page}/{size}")
    public Result findByPid(@PathVariable("parentid")String parentid,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        Page<Spit> pageList = spitService.findByPid(parentid,page,size);
        return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }
    /**
     * 功能描述：吐槽点赞
     * @Author LiHuaMing
     * @Date 19:58 2019/4/17
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/thumbup/{spitId}")
    public Result updateThumbup(@PathVariable("spitId")String spitId){
        String userid="xxx";
        Integer obj = (Integer) redisTemplate.opsForValue().get("thumbup_" + userid + spitId);
        //从缓存中，判断当前用户是否点过赞
        if(null == obj){
            //没有点过赞，则用户可以点赞，插入到缓存中
            spitService.updateThumbup(spitId);
            redisTemplate.opsForValue().set("thumbup_" + userid + spitId,1);
            return new Result(StatusCode.SUCCESS,true,"点赞成功");
        }
        //缓存中存在数据，则表示该用户已经点过赞了
        return new Result(StatusCode.SUCCESS,true,"你已经赞过了");
    }
    /**
     * 功能描述：分页查询全部
     * @Author LiHuaMing
     * @Date 20:28 2019/4/17
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/search/{page}/{size}")
    public Result findByPage(@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        Page<Spit> pageList = spitService.findByPage(page,size);
        return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }
    /**
     * 功能描述：根据条件查询
     * @Author LiHuaMing
     * @Date 20:33 2019/4/17
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public Result findByConditions(@RequestBody Spit spit){
        return new Result(StatusCode.SUCCESS,true,"查询成功",spitService.findByConditions(spit));
    }
    /**
     * 功能描述：分享+1
     * @Author LiHuaMing
     * @Date 14:48 2019/4/18
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/share/{spitId}")
    public Result updateShare(@PathVariable("spitId")String spitId){
        spitService.updateShare(spitId);
        return new Result(StatusCode.SUCCESS,true,"分享成功");
    }
    /**
     * 功能描述：浏览量+1
     * @Author LiHuaMing
     * @Date 14:48 2019/4/18
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/visits/{spitId}")
    public Result updateVisits(@PathVariable("spitId")String spitId){
        spitService.updateVisits(spitId);
        return new Result(StatusCode.SUCCESS,true,"浏览成功");
    }
}
