package com.vue.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Member;
import com.vue.mapper.MemberMapper;
import com.vue.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public IPage<Member> findAll(Page<Member> page) {
        return memberMapper.findAll(page);
    }

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    @Override
    public Member findById(Integer memberId) {
        return memberMapper.findById(memberId);
    }

    @Override
    public int deleteById(Integer memberId) {
        return memberMapper.deleteById(memberId);
    }

    @Override
    public int update(Member member) {
        return memberMapper.update(member);
    }

    @Override
    public int add(Member member) {
        return memberMapper.add(member);
    }
}
