package com.huahua.gathering.controller;
import com.huahua.gathering.pojo.Gathering;
import com.huahua.gathering.service.GatheringService;
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
@RequestMapping("/gathering")
public class GatheringController {

	@Autowired
	private GatheringService gatheringService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){

		return new Result(StatusCode.SUCCESS,true,"查询成功",gatheringService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(StatusCode.SUCCESS,true,"查询成功",gatheringService.findById(id));
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
		Page<Gathering> pageList = gatheringService.findSearch(searchMap, page, size);
		return  new Result(StatusCode.SUCCESS,true,"查询成功",  new PageResult<Gathering>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(StatusCode.SUCCESS,true,"查询成功",gatheringService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param gathering
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Gathering gathering  ){
		gatheringService.add(gathering);
		return new Result(StatusCode.SUCCESS,true,"增加成功");
	}
	
	/**
	 * 修改
	 * @param gathering
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Gathering gathering, @PathVariable String id ){
		gathering.setId(id);
		gatheringService.update(gathering);		
		return new Result(StatusCode.SUCCESS,true,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		gatheringService.deleteById(id);
		return new Result(StatusCode.SUCCESS,true,"删除成功");
	}
	/**
	 * 功能描述：根据城市显示分页内容
	 * @Author LiHuaMing
	 * @Date 11:09 2019/4/16
	 * @Param [city, page, size]
	 * @return huahua.common.Result
	 */
	@RequestMapping(value = "/city/{city}/{page}/{size}",method = RequestMethod.GET)
	public Result getByCity(@PathVariable("city")String city,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
		Page<Gathering> pageList = gatheringService.getByCity(city, page, size);
		return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<Gathering>(pageList.getTotalElements(),pageList.getContent()));
	}
}
