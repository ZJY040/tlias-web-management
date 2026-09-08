package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public interface StudentService {


    PageResult<Student> page(StudentQueryParam studentQueryParam);

    void add(Student student);

    Student getById(Integer id);

    void update(Student student);

    void delete(Integer id);

    void deleteBatch(List<Integer> idList);

    Student handleViolation(Integer id, Integer score);
}
