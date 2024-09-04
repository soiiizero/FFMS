package com.vue.controller;

import com.vue.entity.Admin;
import com.vue.entity.ApiResult;
import com.vue.entity.Member;
import com.vue.serviceimpl.AdminServiceImpl;
import com.vue.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private AdminServiceImpl adminService;
    @Autowired
    public AdminController(AdminServiceImpl adminService){
        this.adminService = adminService;
    }

    @GetMapping("/admins")
    public ApiResult findAll(){
        System.out.println("查询全部");
        return ApiResultHandler.success(adminService.findAll());
    }

    @GetMapping("/admin/{adminId}")
    public ApiResult findById(@PathVariable("adminId") Integer adminId){
        System.out.println("根据ID查找");
        return ApiResultHandler.success(adminService.findById(adminId));
    }

    @DeleteMapping("/admin/{adminId}")
    public ApiResult deleteById(@PathVariable("adminId") Integer adminId){
        return ApiResultHandler.success(adminService.deleteById(adminId));
    }

    @PutMapping("/admin")
    public ApiResult update(@RequestBody Admin admin){
        return ApiResultHandler.success(adminService.update(admin));
    }

    @PostMapping("/admin")
    public ApiResult add(Admin admin){
        return ApiResultHandler.success(adminService.add(admin));
    }
}
