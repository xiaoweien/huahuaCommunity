package com.huahua.es.service;

import com.huahua.es.dao.EsDao;
import com.huahua.es.pojo.ArticleEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EsService {

    @Autowired private EsDao esDao;

    public Page<ArticleEs> searchArticle(String keywords, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return esDao.findByTitleOrContentLike(keywords,keywords,pageRequest);
    }

    public void add(ArticleEs articleEs) {
        esDao.save(articleEs);
    }

    public void delete(String id) {
        esDao.deleteById(id);
    }


    public Page<ArticleEs> findArticle(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return esDao.findAll(pageRequest);
    }

    public void update(ArticleEs articleEs) {
        esDao.save(articleEs);
    }
}
