package com.vue.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.ApiResult;
import com.vue.entity.Pay;
import com.vue.serviceimpl.PayServiceImpl;
import com.vue.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PayController {

    @Autowired
    private PayServiceImpl payService;

    @GetMapping("/pays")
    public ApiResult findAll(){
        System.out.println("不分页查询所有支出");
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", payService.findAll());
        return apiResult;
    }

    @GetMapping("/pays/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        System.out.println("分页查询所有支出");
        ApiResult apiResult;
        Page<Pay> pay = new Page<>(page,size);
        IPage<Pay> all = payService.findAll(pay);
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", all);
        return apiResult;
    }

    @GetMapping("/pay/{payId}")
    public ApiResult findById(@PathVariable("payId") Integer payId){
        System.out.println("根据ID查找");
        Pay res = payService.findById(payId);
        if(res == null) {
            return ApiResultHandler.buildApiResult(10000,"考试编号不存在",null);
        }
        return ApiResultHandler.buildApiResult(200,"请求成功！",res);
    }

    @DeleteMapping("/pay/{payId}")
    public ApiResult deleteById(@PathVariable("payId") Integer payId){
        int res = payService.delete(payId);
        return ApiResultHandler.buildApiResult(200,"删除成功",res);
    }

    @PutMapping("/pay")
    public ApiResult update(@RequestBody Pay pay){
        int res = payService.update(pay);
//        if (res == 0) {
//            return ApiResultHandler.buildApiResult(20000,"请求参数错误");
//        }
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(200,"更新成功",res);
    }

    @PostMapping("/pay")
    public ApiResult add(@RequestBody Pay pay){
        int res = payService.add(pay);
        if (res ==1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        } else {
            return  ApiResultHandler.buildApiResult(400,"添加失败",res);
        }
    }

    @GetMapping("/payId")
    public ApiResult findOnlyPayId() {
        Pay res = payService.findOnlyPayId();
        if (res != null) {
            return ApiResultHandler.buildApiResult(200,"请求成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"请求失败",res);
    }
}
