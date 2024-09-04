package com.vue.controller;

import com.vue.entity.*;
import com.vue.serviceimpl.LoginServiceImpl;
import com.vue.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/login")
    public ApiResult login(@RequestBody Login login) {

        Integer username = login.getUsername();
        String password = login.getPassword();
        Admin adminRes = loginService.adminLogin(username, password);
        if (adminRes != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", adminRes);
        }

        Member memberRes = loginService.memberLogin(username,password);
        if (memberRes != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", memberRes);
        }

        return ApiResultHandler.buildApiResult(400, "请求失败", null);
    }
}
