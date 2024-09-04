package com.vue.service;

import com.vue.entity.Admin;
import com.vue.entity.Member;

public interface LoginService {

    public Admin adminLogin(Integer username, String password);

    public Member memberLogin(Integer username, String password);

}
