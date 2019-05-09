package com.huahua.base.service.impl;

import com.huahua.base.daojpa.BaseDao;
import com.huahua.base.dtojpa.Label;
import com.huahua.base.service.BaseService;
import huahua.common.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BaseServiceImpl implements BaseService {
    @Autowired private IdWorker idWorker;
    @Autowired private BaseDao baseDao;
    @Override
    public void add(Label label) {
        if (StringUtils.isNotEmpty(label.getId())) {
            baseDao.save(label);
        }else{
            //label.setId(idWorker.nextId()+"");
            label.setId(String.valueOf(idWorker.nextId()));//效率快
            baseDao.save(label);
        }
    }

    @Override
    public List<Label> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<Label> toplist(String recommend) {
        return baseDao.findAllByRecommendOrderByFansDesc(recommend);
    }

    @Override
    public List<Label> list(String state) {
        return baseDao.findAllByStateOrderByFansDesc(state);
    }

    @Override
    public Label findById(String labelId) {
        return baseDao.queryById(labelId);
    }

    @Override
    public void delete(String labelId) {
        baseDao.deleteById(labelId);
    }

    @Override
    public Page<Label> search(Label label, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return baseDao.findAll(createSpecification(label),pageRequest);
    }

    /**
     * 功能描述：条件构造器
     * @Author LiHuaMing
     * @Date 21:21 2019/4/11
     * @Param [label]
     * @return org.springframework.data.jpa.domain.Specification<Label>
     */
    private Specification<Label> createSpecification(Label label){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                //创建sql语句
                if (null == label){
                    return null;
                }
                if (StringUtils.isNotEmpty(label.getId())){//where id = ?
                    predicateList.add(cb.equal(root.get("id").as(String.class),label.getId()));
                }
                if (StringUtils.isNotEmpty(label.getLabelname())){//and labelname like "%?%"
                    predicateList.add(cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%"));
                }
                if (null != predicateList && predicateList.size()>0){
                    return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
                }
                return null;
            }
        };
    }
}
