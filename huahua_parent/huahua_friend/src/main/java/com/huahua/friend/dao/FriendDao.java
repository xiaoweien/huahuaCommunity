package com.huahua.friend.dao;

import com.huahua.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> , JpaSpecificationExecutor<Friend> {

    //更新为互相喜欢
    @Modifying
    @Query(nativeQuery = true,value = "update tb_friend set islike = ?3 where userid = ?1 and friendid = ?2")
    void updateLike(String userid,String friendid,String islike);
    //判断当前用户是否关注了当前用户
    @Query(nativeQuery = true,value = "select count(1) from tb_friend where friendid = ?2 and userid = ?1")
    int selectByUserCount(String userid, String friendid);
    @Modifying
    @Query(nativeQuery = true,value = "delete from tb_friend where userid = ?1 and friendid = ?2")
    void deleteByUseridAndAndFriendid(String userid,String friendid);

}
