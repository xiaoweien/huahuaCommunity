package com.huahua.qa.controller;

import com.huahua.qa.eureka.LabelEureka;
import com.huahua.qa.pojo.Problem;
import com.huahua.qa.service.ProblemService;
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
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;

	/**
	 * 功能描述：调用huahua-base
	 * @Author LiHuaMing
	 * @Date 13:55 2019/4/28
	 * @Param
	 * @return
	 */
	@Autowired
	private LabelEureka labelEureka;
	@RequestMapping(value = "/label/{labelid}")
	public Result findLabelById(@PathVariable("labelid")String labelid) {
		Result result = labelEureka.findById(labelid);
		return result;
	}

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
		return new Result(StatusCode.SUCCESS,true,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(StatusCode.SUCCESS,true,"查询成功",problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(StatusCode.SUCCESS,true,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(StatusCode.SUCCESS,true,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		problemService.add(problem);
		return new Result(StatusCode.SUCCESS,true,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(StatusCode.SUCCESS,true,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(StatusCode.SUCCESS,true,"删除成功");
	}

	/**
	 * 功能描述：最新问答
	 * @Author LiHuaMing
	 * @Date 19:14 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/newlist/{labelid}/{page}/{size}")
	public Result newlist(@PathVariable("labelid")String labelid,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
		Page<Problem> problemPage = problemService.newlist(labelid, page, size);
		return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<>(problemPage.getTotalElements(),problemPage.getContent()));
	}
	/**
	 * 功能描述：热门问答
	 * @Author LiHuaMing
	 * @Date 19:15 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/hotlist/{labelid}/{page}/{size}")
	public Result hotlist(@PathVariable("labelid")String labelid,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
		Page<Problem> problemPage = problemService.hotlist(labelid, page, size);
		return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<>(problemPage.getTotalElements(),problemPage.getContent()));
	}
	/**
	 * 功能描述：等待问答
	 * @Author LiHuaMing
	 * @Date 19:15 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/waitlist/{labelid}/{page}/{size}")
	public Result waitlist(@PathVariable("labelid")String labelid,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
		Page<Problem> problemPage = problemService.waitlist(labelid, page, size);
		return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<>(problemPage.getTotalElements(),problemPage.getContent()));
	}
}
