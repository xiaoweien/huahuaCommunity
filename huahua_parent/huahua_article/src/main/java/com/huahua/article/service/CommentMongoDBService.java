package com.huahua.article.service;

import com.huahua.article.dao.CommentMongoDBDao;
import com.huahua.article.pojo.CommentMongoDB;
import huahua.common.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentMongoDBService {

    @Autowired private CommentMongoDBDao commentMongoDBDao;

    @Autowired private IdWorker idWorker;

    public void add(CommentMongoDB commentMongoDB){
        commentMongoDB.set_id(String.valueOf(idWorker.nextId()));
        commentMongoDB.setPublishtime(new Date());
        commentMongoDBDao.save(commentMongoDB);
    }

    public List<CommentMongoDB> queryByArticleid(String articleid){
        return commentMongoDBDao.findAllByArticleidOrderByPublishtimeDesc(articleid);
    }

    public void delete(String ids){
        if (StringUtils.isNotEmpty(ids)){
            String[] split = ids.split(",");
            for (String s : split) {
                commentMongoDBDao.deleteById(s);
            }
        }
    }
}
