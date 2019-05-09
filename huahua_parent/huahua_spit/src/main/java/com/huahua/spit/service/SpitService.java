package com.huahua.spit.service;

import com.huahua.spit.dao.SpitDao;
import com.huahua.spit.pojo.Spit;
import huahua.common.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired private SpitDao spitDao;

    @Autowired private IdWorker idWorker;

    @Autowired private MongoTemplate mongoTemplate;

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id){
        Spit spit = spitDao.findById(id).get();
        return spit;
    }

    public void add(Spit spit){
        spit.set_id(String.valueOf(idWorker.nextId()));
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //如果有父id 回复数Comment++
        if (StringUtils.isNotEmpty(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        //如果没有父id 则是第一条评论
        spitDao.save(spit);
    }

    public void update(Spit spit){
        spitDao.save(spit);
    }

    public void delete(String id){
        spitDao.deleteById(id);
    }

    public Page<Spit> findByPid(String parentid, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return spitDao.findByParentid(parentid,pageRequest);
    }

    public void updateThumbup(String spitId) {
        Query query = new Query();//创建查询语句
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();//创建修改语句
        //update.addToSet()     update table set key = value where _id = spitId
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    public Page<Spit> findByPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return spitDao.findAll(pageRequest);
    }

    public List<Spit> findByConditions(Spit spit) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("_id").is(spit.get_id()),
                Criteria.where("content").is(spit.getContent()),
                Criteria.where("publishtime").is(spit.getPublishtime()),
                Criteria.where("userid").is(spit.getUserid()),
                Criteria.where("nickname").is(spit.getNickname()),
                Criteria.where("visits").is(spit.getVisits()),
                Criteria.where("thumbup").is(spit.getThumbup()),
                Criteria.where("share").is(spit.getShare()),
                Criteria.where("comment").is(spit.getComment()),
                Criteria.where("state").is(spit.getState()),
                Criteria.where("parentid").is(spit.getParentid()));
        query.addCriteria(criteria);
        return mongoTemplate.find(query,Spit.class);
    }

    public void updateShare(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("share",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    public void updateVisits(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("visits",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
