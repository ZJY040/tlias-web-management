package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;
    //@RequestMapping(value = "/depts",method = RequestMethod.GET) //method指定请求方法为GET
    @GetMapping("/depts")
    public Result list(){
        System.out.println("查询全部的部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 删除部门 - 方式一：
     * HttpServletRequest 获取请求参数
     */
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request){
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//        System.out.println("根据ID删除部门： " + id);
//        return Result.success();
//    }
    /**
     * 删除部门 - 方式二：@RequestParam
     * 注意事项： 一旦声明了 @RequestParam，该参数在请求时必须传递，如果不传递将会报错（默认 required 为true）
     */
//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam("id") Integer deptId){
//        System.out.println("根据ID删除部门： " + deptId);
//        return Result.success();
//    }

    /**
     * 删除部门 - 方式三：省略@RequestParam（前端传递的请求参数名与服务端方法形参名一致）(推荐)
     */
    @DeleteMapping("/depts")
    public Result delete( Integer id){
        System.out.println("根据ID删除部门： " + id);
        deptService.deleteById(id);
        return Result.success();
    }
    /**
     *  新增部门
     */
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
        System.out.println("新增部门[] " +dept);
        deptService.add(dept);
        return Result.success();
    }
    /**
     * 根据ID查询部门
     */
//    @GetMapping("/depts/{id}")
//    public Result getInfo(@PathVariable("id") Integer deptId){
//        System.out.println("根据ID查询部门[] " + deptId);
//        return Result.success();
//    }

    //
    @GetMapping("/depts/{id:\\d+}")
    public Result getInfo(@PathVariable Integer id){
        System.out.println("根据ID查询部门[] " + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
        System.out.println("修改部门数据 " + dept);
        deptService.update(dept);
        return Result.success();
    }
}