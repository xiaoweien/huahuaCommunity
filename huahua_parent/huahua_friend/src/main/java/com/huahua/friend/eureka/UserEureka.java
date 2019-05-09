package com.huahua.friend.eureka;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("huahua-user")
public interface UserEureka {

    /**
     * 功能描述：添加粉丝数
     * @Author LiHuaMing
     * @Date 0:04 2019/4/29
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/user/incfollow/{userid}/{num}")
    void incfollowCount(@PathVariable("userid")String userid, @PathVariable("num")Integer num);
    /**
     * 功能描述：增加粉丝数(huahua-friend)
     * @Author LiHuaMing
     * @Date 23:15 2019/4/28
     * @Param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/user/incfans/{userid}/{num}")
    void incfansCount(@PathVariable("userid")String userid,@PathVariable("num")Integer num);
}
