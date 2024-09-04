package com.vue.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Pay;

import java.util.List;

public interface PayService {

    /**
     * 不分页查询所有考试信息
     */
    List<Pay> findAll();
    IPage<Pay> findAll(Page<Pay> page);

    Pay findById(Integer payCode);

    int delete(Integer payCode);

    int update(Pay pay);

    int add(Pay pay);

    Pay findOnlyPayId();
}
