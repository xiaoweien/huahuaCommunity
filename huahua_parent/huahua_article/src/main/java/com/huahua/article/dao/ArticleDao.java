package com.huahua.article.dao;

import com.huahua.article.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    @Query(nativeQuery = true,value = "select * from huahua_article.tb_article where id = ?1")
    Article findOneById(String articleid);

    //@Modifying 如果直接执行增删改的方法，需要加上@Modifying的注解，否则没有效果
    @Modifying
    @Query(nativeQuery = true,value = "update huahua_article.tb_article set state = '1' where id = ?1")
    void updateArticleStateByArticleId(String articleid);

    @Modifying
    @Query(nativeQuery = true,value = "update huahua_article.tb_article set thumbup = thumbup+1 where id = ?1")
    void updateArticleThumbup(String articleid);

    Page<Article> findAllByChannelid(String channelid, Pageable pageable);

    Page<Article> findAllByColumnid(String columnid, Pageable pageable);

    @Query(nativeQuery = true,value = "select * from huahua_article.tb_article where istop = ?1")
    Article headline(String istop);
}
