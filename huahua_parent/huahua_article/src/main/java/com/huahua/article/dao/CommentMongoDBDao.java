package com.huahua.article.dao;

import com.huahua.article.pojo.CommentMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentMongoDBDao extends MongoRepository<CommentMongoDB,String> {

    List<CommentMongoDB> findAllByArticleidOrderByPublishtimeDesc(String artocleid);

}
