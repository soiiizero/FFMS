package com.vue.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.Member;

import java.util.List;

public interface MemberService {

    IPage<Member> findAll(Page<Member> page);

    public List<Member> findAll();

    public Member findById(Integer memberId);

    public int deleteById(Integer memberId);

    public int update(Member member);

    public int add(Member member);
}
