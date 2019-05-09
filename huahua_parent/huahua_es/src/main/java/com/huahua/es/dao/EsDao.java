package com.huahua.es.dao;

import com.huahua.es.pojo.ArticleEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsDao extends ElasticsearchRepository<ArticleEs,String> {

    //select * from table where title = keywords or content = keywords
    Page<ArticleEs> findByTitleOrContentLike(String title, String content, Pageable pageable);

}
