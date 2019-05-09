package com.huahua.article.controller;
import com.huahua.article.common.StaticParams;
import com.huahua.article.pojo.Article;
import com.huahua.article.service.ArticleService;
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
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(StatusCode.SUCCESS,true,"查询成功",articleService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(StatusCode.SUCCESS,true,"查询成功",articleService.findById(id));
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
		Page<Article> pageList = articleService.findSearch(searchMap, page, size);
		return  new Result(StatusCode.SUCCESS,true,"查询成功",  new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(StatusCode.SUCCESS,true,"查询成功",articleService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param article
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Article article  ){
		articleService.add(article);
		return new Result(StatusCode.SUCCESS,true,"增加成功");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Article article, @PathVariable String id ){
		article.setId(id);
		articleService.update(article);		
		return new Result(StatusCode.SUCCESS,true,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		articleService.deleteById(id);
		return new Result(StatusCode.SUCCESS,true,"删除成功");
	}
	/**
	 * 功能描述：修改文章的审批状态
	 * @Author LiHuaMing
	 * @Date 20:44 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "/examine/{articleid}")
	public Result updateArticleStateByArticleId(@PathVariable("articleid")String articleid){
		articleService.updateArticleStateByArticleId(articleid);
		return new Result(StatusCode.SUCCESS,true,"审批通过");
	}
	/**
	 * 功能描述：点赞
	 * @Author LiHuaMing
	 * @Date 20:44 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "/thumbup/{articleid}")
	public Result updateArticleThumbup(@PathVariable("articleid")String articleid){
		articleService.updateArticleThumbup(articleid);
		return new Result(StatusCode.SUCCESS,true,"点赞成功");
	}
	/**
	 * 功能描述：根据频道id获取文章列表
	 * @Author LiHuaMing
	 * @Date 21:18 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/channel/{channelid}/{page}/{size}")
	public Result findAllByChannelid(@PathVariable("channelid")String channelid,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
		Page<Article> articlePage = articleService.findAllByChannelid(channelid, page, size);
		return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<>(articlePage.getTotalElements(),articlePage.getContent()));
	}
	/**
	 * 功能描述：根据专栏id获取文章列表
	 * @Author LiHuaMing
	 * @Date 21:18 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/column/{columnid}/{page}/{size}")
	public Result findAllByColumnid(@PathVariable("columnid")String columnid,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
		Page<Article> articlePage = articleService.findAllByColumnid(columnid, page, size);
		return new Result(StatusCode.SUCCESS,true,"查询成功",new PageResult<>(articlePage.getTotalElements(),articlePage.getContent()));
	}
	/**
	 * 功能描述：头条文章
	 * @Author LiHuaMing
	 * @Date 21:18 2019/4/12
	 * @Param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/top")
	public Result headline(){
		return new Result(StatusCode.SUCCESS,true,"查询成功",articleService.headline(StaticParams.IS_TOP));
	}
}
