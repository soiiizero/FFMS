package com.vue.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Income;

import java.util.List;

public interface IncomeService {

    /**
     * 不分页查询所有信息
     */
    List<Income> findAll();
    IPage<Income> findAll(Page<Income> page);

    Income findById(Integer incomeCode);

    int delete(Integer incomeCode);

    int update(Income income);

    int add(Income income);

    Income findOnlyIncomeId();
}
