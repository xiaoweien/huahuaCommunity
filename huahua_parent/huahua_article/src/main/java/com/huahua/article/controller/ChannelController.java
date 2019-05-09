package com.huahua.article.controller;

import com.huahua.article.pojo.Channel;
import com.huahua.article.service.ChannelService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		Claims claims = (Claims) httpServletRequest.getAttribute("admin_claims");
		if (null == claims){
			return new Result(StatusCode.NOT_POWER,false,"权限不足");
		}
		return new Result(StatusCode.SUCCESS,true,"查询成功",channelService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(StatusCode.SUCCESS,true,"查询成功",channelService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Channel> pageList = channelService.findSearch(searchMap, page, size);
		return  new Result(StatusCode.SUCCESS,true,"查询成功",  new PageResult<Channel>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(StatusCode.SUCCESS,true,"查询成功",channelService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param channel
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Channel channel  ){
		channelService.add(channel);
		return new Result(StatusCode.SUCCESS,true,"增加成功");
	}
	
	/**
	 * 修改
	 * @param channel
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Channel channel, @PathVariable String id ){
		channel.setId(id);
		channelService.update(channel);		
		return new Result(StatusCode.SUCCESS,true,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		channelService.deleteById(id);
		return new Result(StatusCode.SUCCESS,true,"删除成功");
	}
	
}
