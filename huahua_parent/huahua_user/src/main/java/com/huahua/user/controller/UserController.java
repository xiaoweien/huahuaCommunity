package com.huahua.user.controller;

import com.huahua.user.pojo.User;
import com.huahua.user.service.UserService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import huahua.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private JwtUtil jwtUtil;
	
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
		return new Result(StatusCode.SUCCESS,true,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(StatusCode.SUCCESS,true,"查询成功",userService.findById(id));
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
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(StatusCode.SUCCESS,true,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(StatusCode.SUCCESS,true,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(StatusCode.SUCCESS,true,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(StatusCode.SUCCESS,true,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
//		//需求：删除用户，必须拥有管理员权限，否则不能删除
//		//前后端约定：前端请求微服务时需要添加头信息Authorization，内容为Bearer+空格+token
//		//获取请求头中的数据
//		//Authorization:Bearer xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//		String header = httpServletRequest.getHeader("Authorization");//Authorization参数名称
//		if (StringUtils.isEmpty(header)){
//			return new Result(StatusCode.LOGIN_ERROR,false,"登录有误，请返回重新登录");
//		}
//		//Bearer也为null	header.startsWith查询字符串中以谋改革name开头的数据是否存在的判断
//		if (!header.startsWith("Bearer ")){
//			return new Result(StatusCode.LOGIN_ERROR,false,"登录有误，请返回重新登录");
//		}
//		String token = header.substring(7);
//		//token解析后的明文数据Claims
//		Claims claims = null;
//		try {
//			claims = jwtUtil.parseJWT(token);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		//校验claims不能为空
//		if (null == claims){
//			return new Result(StatusCode.LOGIN_ERROR,true,"登录异常");
//		}
//		//判断，必须拥有管理员权限，否则不能删除
//		if (!StringUtils.equals("admin", (String) claims.get("roles"))){
//			return new Result(StatusCode.NOT_POWER,true,"权限不足");
//		}
		//只有admin可以删除
		Claims claims = (Claims) httpServletRequest.getAttribute("admin_claims");
		if (null == claims){
			return new Result(StatusCode.NOT_POWER,false,"权限不足");
		}
		userService.deleteById(id);
		return new Result(StatusCode.SUCCESS,true,"删除成功");
	}
	/**
	 * 功能描述：发送验证码
	 * @Author LiHuaMing
	 * @Date 15:43 2019/4/24
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value="/sendsms/{mobile}")
	public Result sendsms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(StatusCode.SUCCESS,true,"发送成功");
	}
	/**
	 * 功能描述：注册
	 * @Author LiHuaMing
	 * @Date 15:26 2019/4/24
	 * @Param
	 * @return
	 */
//	@RequestMapping(method = RequestMethod.POST,value = "/register/{code}")
	@PostMapping(value = "/register/{code}")
	public Result registerUser(@PathVariable("code")String code,@RequestBody User user){
		userService.registerUser(code,user);
		return new Result(StatusCode.SUCCESS,true,"注册成功");
	}
	/**
	 * 功能描述：登录
	 * @Author LiHuaMing
	 * @Date 8:48 2019/4/25
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/login")
	public Result login(@RequestBody User user){
		if (null == user || StringUtils.isEmpty(user.getMobile())){
			return new Result(StatusCode.ERROR,false,"参数有误");
		}
		if (null == user || StringUtils.isEmpty(user.getPassword())){
			return new Result(StatusCode.ERROR,false,"参数有误");
		}
		User u = userService.login(user);
		if (null != u){
			//生成token(JTW) 处理跟用户一系列相关的信息，比如：权限的查询，用户一些相关的业务
			String token = jwtUtil.createJWT(u.getId(), u.getMobile(), "user");
			//JWT前端与后端访问的唯一标识，都要校验token，否则都会让操作的用户返回登录
			//把token数据返回给前端(身份唯一标识)
			HashMap<String, Object> map = new HashMap<>();
			map.put("token",token);
			map.put("tel",u.getMobile());
			map.put("name",u.getNickname());//昵称
			map.put("avatar",u.getAvatar());//
			System.out.println(token);
			httpServletRequest.getSession().setAttribute("USER_LOGIN",u);
			return new Result(StatusCode.SUCCESS,true,"登陆成功",map);
		}else {
			return new Result(StatusCode.LOGIN_ERROR,false,"用户名或密码错误");
		}

	}
	/**
	 * 功能描述：查询登陆用户信息
	 * @Author LiHuaMing
	 * @Date 23:48 2019/4/24
	 * @Param [code, user]
	 * @return huahua.common.Result
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/info")
	public Result findUser(){
		User user = (User) httpServletRequest.getAttribute("USER_LOGIN");
		return new Result(StatusCode.SUCCESS,true,"查询成功",userService.findUser(user.getId()));
	}
	/**
	 * 功能描述：修改当前登陆用户信息
	 * @Author LiHuaMing
	 * @Date 8:35 2019/4/25
	 * @Param 
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "/saveinfo")
	public Result updateUser(@RequestBody User user){
		User u = (User) httpServletRequest.getAttribute("USER_LOGIN");
		User u2 = userService.findUser(u.getId());
		user.setId(u2.getId());
		userService.updateUser(user);
		return new Result(StatusCode.SUCCESS,true,"修改成功");
	}
	/**
	 * 功能描述：增加关注数(huahua-friend)
	 * @Author LiHuaMing
	 * @Date 23:15 2019/4/28
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/incfollow/{userid}/{num}")
	public void incfollowCount(@PathVariable("userid")String userid,@PathVariable("num")Integer num){
		userService.incfollowCount(userid,num);
	}
	/**
	 * 功能描述：增加粉丝数(huahua-friend)
	 * @Author LiHuaMing
	 * @Date 23:15 2019/4/28
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/incfans/{userid}/{num}")
	public void incfansCount(@PathVariable("userid")String userid,@PathVariable("num")Integer num){
		userService.incfansCount(userid,num);
	}
}
