package com.vue.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.ApiResult;
import com.vue.entity.Member;
import com.vue.serviceimpl.MemberServiceImpl;
import com.vue.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private MemberServiceImpl memberService;
    @Autowired
    public MemberController(MemberServiceImpl memberService){
        this.memberService = memberService;
    }

    @GetMapping("/members/{page}/{size}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size){
        Page<Member> memberPage = new Page<>(page,size);
        IPage<Member> memberIPage = memberService.findAll(memberPage);

        return ApiResultHandler.buildApiResult(200,"查询所有教师",memberIPage);
    }

    @GetMapping("/member/{memberId}")
    public ApiResult findById(@PathVariable("memberId") Integer memberId){
        return ApiResultHandler.success(memberService.findById(memberId));
    }

    @DeleteMapping("/member/{memberId}")
    public ApiResult deleteById(@PathVariable("memberId") Integer memberId){
        return ApiResultHandler.success(memberService.deleteById(memberId));
    }

    @PutMapping("/member")
    public ApiResult update(@RequestBody Member member){
        return ApiResultHandler.success(memberService.update(member));
    }

    @PostMapping("/member")
    public ApiResult add(@RequestBody Member member){
        return ApiResultHandler.success(memberService.add(member));
    }
}
