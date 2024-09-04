package com.vue.serviceimpl;

import com.vue.entity.Admin;
import com.vue.entity.Member;
import com.vue.mapper.LoginMapper;
import com.vue.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Admin adminLogin(Integer username, String password) {
        return loginMapper.adminLogin(username,password);
    }

    @Override
    public Member memberLogin(Integer username, String password) {
        return loginMapper.memberLogin(username,password);
    }

}
