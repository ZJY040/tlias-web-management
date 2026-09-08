package com.itheima.controller;

import com.itheima.dto.ViolationRequest;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(StudentQueryParam studentQueryParam){
        log.info("条件分页查询：{}",studentQueryParam);
        PageResult<Student> PageResult= studentService.page(studentQueryParam);
        return Result.success(PageResult);
    }
    @PostMapping
    public Result add(@RequestBody Student student){
        log.info("新增学生信息");
        studentService.add(student);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据id查询学员信息:{}",id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id,@RequestBody Student student){
        log.info("修改学员数据:id,{},student,{}",id,student);
        student.setId(id);
        studentService.update(student);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除学员数据:id,{}",id);
        studentService.delete(id);
        return Result.success();
    }
    /**
     * 批量删除学员
     * DELETE /students?ids=1,2,3
     */
    @DeleteMapping
    public Result deleteBatch(@RequestParam String ids) {
        log.info("批量删除学员，ids：{}", ids);
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        studentService.deleteBatch(idList);
        return Result.success();
    }
    @PutMapping("/violation/{id}")
    public Result handleViolation(@PathVariable Integer id, @RequestBody ViolationRequest violationRequest){
        log.info("违纪处理，学员ID：{}，扣分：{}", id, violationRequest.getScore());
        Student student = studentService.handleViolation(id, violationRequest.getScore());
        return Result.success(student);
    }

}
