package com.huahua.recruit.dao;

import com.huahua.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    //推荐职位
    List<Recruit> findTop4AllByState(String state);
    //最新职位
    @Query(nativeQuery = true,value = "select * from huahua_recruit.tb_recruit where 1 = 1 and state <> 0 order by createtime desc")
    List<Recruit> queryByStateRecruitList(String state);
}
