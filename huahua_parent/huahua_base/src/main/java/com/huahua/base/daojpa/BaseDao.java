package com.huahua.base.daojpa;

import com.huahua.base.dtojpa.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
//JpaRepository 提供了基本的增删查改(CURD)
//JpaSpecificationExecutor  用于做复杂的条件查询
public interface BaseDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {

    List<Label> findAllByRecommendOrderByFansDesc(String recommend);

    List<Label> findAllByStateOrderByFansDesc(String state);

    //@Query 查询语句   nativeQuery 执行sql语句   "?"单个传参，若果多个参数，以"?1"开始 依次累加
    @Query(value = "select * from tb_label where id = ?1",nativeQuery = true)
    Label queryById(String labelId);
}
