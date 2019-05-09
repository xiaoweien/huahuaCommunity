package com.huahua.friend.controller;

import com.huahua.friend.service.FriendService;
import huahua.common.Result;
import huahua.common.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest httpServletRequest;
    /**
     * 功能描述：添加关注获取取消关注
     * @Author LiHuaMing
     * @Date 19:23 2019/4/28
     * @Param friendid：被关注的用户   type：1喜欢 2不喜欢
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/like/{friendid}/{type}")
    public Result addFriend(@PathVariable("friendid")String friendid,@PathVariable("type")String type){
        //判断值是否存在
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if (null == claims){
            return new Result(StatusCode.NOT_POWER,false,"权限不足");
        }
        //判断type类型是 1喜欢 2不喜欢
        //如果喜欢
        if (StringUtils.equals("1",type)){
            if (friendService.addFriend(claims.getId(),friendid) == 0){
                return new Result(StatusCode.ERROR,false,"已关注用户");
            }
        }else {
            //取消喜欢
            friendService.notLikeFriend(claims.getId(),friendid);
        }
        //返回状态
        return new Result(StatusCode.SUCCESS,true,"操作成功");
    }
    /**
     * 功能描述：不喜欢(拉黑)
     * @Author LiHuaMing
     * @Date 21:43 2019/4/28
     * @Param
     * @return
     */
    @DeleteMapping("/{friendid}")
    public Result delete(@PathVariable("friendid")String friendid){
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if (null == claims){
            return new Result(StatusCode.NOT_POWER,false,"权限不足");
        }
        friendService.delete(claims.getId(),friendid);
        return new Result(StatusCode.SUCCESS,true,"操作成功");
    }
}
