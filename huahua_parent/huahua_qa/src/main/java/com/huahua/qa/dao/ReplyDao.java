package com.huahua.qa.dao;

import com.huahua.qa.pojo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ReplyDao extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply>{

    //根据问题id查询回答的列表
    List<Reply> findAllByProblemidOrderByCreatetimeDesc(String problemid);

}
