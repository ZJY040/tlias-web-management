package com.itheima.controller;

import com.itheima.pojo.*;
import com.itheima.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;
    @GetMapping("/list")
    public Result find(){
        log.info("查询班级信息");
        List<Clazz> clazzList = clazzService.findAll();
        return Result.success(clazzList);
    }
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam){
        log.info("分页查询： {}",clazzQueryParam);
        PageResult<Clazz> pageResult= clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据id查询班级信息");
        Clazz clazz = clazzService.getById(id);
        return Result.success(clazz);
    }
    @PostMapping
    public Result add(@RequestBody Clazz clazz){
        log.info("新增班级信息,{}",clazz);
        clazzService.add(clazz);
        return Result.success();
    }
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id,@RequestBody Clazz clazz){
        log.info("=== update 方法被调用了！id: {} ===", id);
        log.info("修改班级信息，id：{},clazz:{}",id,clazz);
        clazz.setId(id);
        clazzService.update(clazz);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除班级信息，id:{}",id);
        clazzService.delete(id);
        return Result.success();
    }
}
