package com.vue.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Income;
import com.vue.mapper.IncomeMapper;
import com.vue.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeMapper incomeMapper;


    @Override
    public List<Income> findAll() {
        return incomeMapper.findAll();
    }

    @Override
    public IPage<Income> findAll(Page<Income> page) {
        return incomeMapper.findAll(page);
    }

    @Override
    public Income findById(Integer incomeId) {
        return incomeMapper.findById(incomeId);
    }

    @Override
    public int delete(Integer incomeId) {
        return incomeMapper.delete(incomeId);
    }

    @Override
    public int update(Income income) {
        return incomeMapper.update(income);
    }

    @Override
    public int add(Income income) {
        return incomeMapper.add(income);
    }

    @Override
    public Income findOnlyIncomeId() {
        return incomeMapper.findOnlyIncomeId();
    }
}
