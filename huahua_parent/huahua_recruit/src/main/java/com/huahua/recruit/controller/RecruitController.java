package com.huahua.recruit.controller;

import com.huahua.recruit.common.StaticParams;
import com.huahua.recruit.pojo.Recruit;
import com.huahua.recruit.service.RecruitService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(StatusCode.SUCCESS,true,"查询成功",recruitService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(StatusCode.SUCCESS,true,"查询成功",recruitService.findById(id));
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
		Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
		return  new Result(StatusCode.SUCCESS,true,"查询成功",  new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(StatusCode.SUCCESS,true,"查询成功",recruitService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param recruit
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Recruit recruit  ){
		recruitService.add(recruit);
		return new Result(StatusCode.SUCCESS,true,"增加成功");
	}
	
	/**
	 * 修改
	 * @param recruit
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Recruit recruit, @PathVariable String id ){
		recruit.setId(id);
		recruitService.update(recruit);		
		return new Result(StatusCode.SUCCESS,true,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		recruitService.deleteById(id);
		return new Result(StatusCode.SUCCESS,true,"删除成功");
	}
	/**
	 * 功能描述：查询推荐的职位
	 * @Author LiHuaMing
	 * @Date 15:04 2019/4/12
	 * @Param []
	 * @return huahua.common.Result
	 */
	@RequestMapping(method= RequestMethod.GET,value="/search/recommend")
	public Result findAllByState(){
		return new Result(StatusCode.SUCCESS,true,"查询成功",recruitService.findAllByState(StaticParams.RECOMMEND_STATE));
	}
	/**
	 * 功能描述：查询最新的职位
	 * @Author LiHuaMing
	 * @Date 15:04 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET,value="/search/newlist")
	public Result queryByStateRecruitList(){
		return new Result(StatusCode.SUCCESS,true,"查询成功",recruitService.queryByStateRecruitList(StaticParams.RECOMMEND_STATE_CLOSE));
	}
}
