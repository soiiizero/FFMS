package com.vue.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Pay;
import com.vue.mapper.PayMapper;
import com.vue.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private PayMapper payMapper;


    @Override
    public List<Pay> findAll() {
        return payMapper.findAll();
    }

    @Override
    public IPage<Pay> findAll(Page<Pay> page) {
        return payMapper.findAll(page);
    }

    @Override
    public Pay findById(Integer payId) {
        return payMapper.findById(payId);
    }

    @Override
    public int delete(Integer payId) {
        return payMapper.delete(payId);
    }

    @Override
    public int update(Pay pay) {
        return payMapper.update(pay);
    }

    @Override
    public int add(Pay pay) {
        return payMapper.add(pay);
    }

    @Override
    public Pay findOnlyPayId() {
        return payMapper.findOnlyPayId();
    }
}
