package com.huahua.base.service;

import com.huahua.base.dtojpa.Label;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseService {
    //添加标签 or 修改标签
    void add(Label label);
    //标签全部列表
    List<Label> findAll();
    //推荐标签列表
    List<Label> toplist(String recommend);
    //有效标签列表
    List<Label> list(String state);
    //根据id查询标签
    Label findById(String labelId);
    //删除标签
    void delete(String labelId);
    //分页
    Page<Label> search(Label label, Integer page, Integer size);
}
