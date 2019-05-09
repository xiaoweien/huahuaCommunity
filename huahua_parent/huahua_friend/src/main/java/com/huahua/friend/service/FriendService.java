package com.huahua.friend.service;

import com.huahua.friend.dao.FriendDao;
import com.huahua.friend.dao.NoFriendDao;
import com.huahua.friend.eureka.UserEureka;
import com.huahua.friend.pojo.Friend;
import com.huahua.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserEureka userEureka;

    public int addFriend(String userid,String friendid){
        //判断用户是否已经添加了这个好友，添加了则不进行任何操作，并返回0
        //(select count(1) from tb_friend where userid = ? and friend = ?)>0 则已经关注过了
        if (friendDao.selectByUserCount(userid,friendid)>0){
            return 0;
        }
        //没有添加则向 friend表 中添加记录
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断对方是否也喜欢你，如果对方也喜欢你，两人的islike = 1
        if (friendDao.selectByUserCount(friendid,userid)>0){
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }
        //调用spring cloud微服务，修改用户表的关注数、粉丝数
        //userA：关注数：1   粉丝数：1
        //userB：关注数：1   粉丝数：1
        //如果A关注B    关注数A+1和粉丝数B+1
        //userid修改的是关注数
        userEureka.incfollowCount(userid,1);
        //friendid修改的是粉丝数
        userEureka.incfansCount(friendid,1);
        return 1;
    }


    public void notLikeFriend(String userid, String friendid) {
        //删除用户数据
        friendDao.deleteByUseridAndAndFriendid(userid,friendid);
        //判断是否互相关注
        //如果互粉，则修改friendid用户中islike
        if (friendDao.selectByUserCount(friendid,userid)>0){
            friendDao.updateLike(friendid,userid,"0");
        }
        //userid修改的是关注数
        userEureka.incfollowCount(userid,-1);
        //friendid修改的是粉丝数
        userEureka.incfansCount(friendid,-1);
    }

    public void delete(String userid,String friendid) {
        //判断当前用户是否喜欢过对方
        if (friendDao.selectByUserCount(userid,friendid)>0){
            //userid修改的是关注数
            userEureka.incfollowCount(userid,-1);
            //friendid修改的是粉丝数
            userEureka.incfansCount(friendid,-1);
        }

        //删除我关注用户的那条记录
        friendDao.deleteByUseridAndAndFriendid(userid,friendid);

        //如果是 互粉，拉黑，islike = 0
        if (friendDao.selectByUserCount(friendid,userid)>0){
            friendDao.updateLike(friendid,userid,"0");
        }
        //tb_nofriend中插入一条记录
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        //调用spring cloud微服务 修改用户表的关注数，粉丝数
    }
}
