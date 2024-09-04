package com.vue.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vue.entity.ApiResult;
import com.vue.entity.Income;
import com.vue.serviceimpl.IncomeServiceImpl;
import com.vue.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IncomeController {

    @Autowired
    private IncomeServiceImpl incomeService;

    @GetMapping("/incomes")
    public ApiResult findAll(){
        System.out.println("不分页查询所有收入");
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", incomeService.findAll());
        return apiResult;
    }

    @GetMapping("/incomes/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        System.out.println("分页查询所有收入");
        ApiResult apiResult;
        Page<Income> income = new Page<>(page,size);
        IPage<Income> all = incomeService.findAll(income);
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", all);
        return apiResult;
    }

    @GetMapping("/income/{incomeId}")
    public ApiResult findById(@PathVariable("incomeId") Integer incomeId){
        System.out.println("根据ID查找");
        Income res = incomeService.findById(incomeId);
        if(res == null) {
            return ApiResultHandler.buildApiResult(10000,"编号不存在",null);
        }
        return ApiResultHandler.buildApiResult(200,"请求成功！",res);
    }

    @DeleteMapping("/income/{incomeId}")
    public ApiResult deleteById(@PathVariable("incomeId") Integer incomeId){
        int res = incomeService.delete(incomeId);
        return ApiResultHandler.buildApiResult(200,"删除成功",res);
    }

    @PutMapping("/income")
    public ApiResult update(@RequestBody Income income){
        int res = incomeService.update(income);
//        if (res == 0) {
//            return ApiResultHandler.buildApiResult(20000,"请求参数错误");
//        }
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(200,"更新成功",res);
    }

    @PostMapping("/income")
    public ApiResult add(@RequestBody Income income){
        int res = incomeService.add(income);
        if (res ==1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        } else {
            return  ApiResultHandler.buildApiResult(400,"添加失败",res);
        }
    }

    @GetMapping("/incomeId")
    public ApiResult findOnlyIncomeId() {
        Income res = incomeService.findOnlyIncomeId();
        if (res != null) {
            return ApiResultHandler.buildApiResult(200,"请求成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"请求失败",res);
    }
}
