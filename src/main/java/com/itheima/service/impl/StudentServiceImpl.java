package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.globalexceptionhandler.BusinessException;
import com.itheima.mapper.ClazzMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(),studentQueryParam.getPageSize());
        List<Student> studentList= studentMapper.selectStudentpage(studentQueryParam);
        PageInfo<Student> Pageinfo = new PageInfo<>(studentList);
        return new PageResult<>(Pageinfo.getTotal(),Pageinfo.getList());
    }

    @Override
    public Student handleViolation(Integer id, Integer score) {
        // 1. 校验扣分分值（前端可能传负数或0）
        if (score == null || score <= 0) {
            throw new BusinessException("扣分分值必须为正整数");
        }

        // 2. 查询学员是否存在
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new BusinessException("学员不存在");
        }

        // 3. 计算新值
        Integer newViolationCount = student.getViolationCount() + 1;
        Integer newViolationScore = student.getViolationScore() + score;

        // 4. 更新数据库
        studentMapper.updateViolation(id, newViolationCount, newViolationScore);

        // 5. 查询更新后的数据返回给前端（便于更新页面显示）
        Student updatedStudent = studentMapper.selectById(id);
        log.info("违纪处理成功，学员ID：{}，当前违纪次数：{}，当前扣分：{}",
                id, updatedStudent.getViolationCount(), updatedStudent.getViolationScore());
        return updatedStudent;
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("请选择要删除的学员");
        }

        // 1. 校验所有学员是否存在（可选）
        for (Integer id : ids) {
            Student student = studentMapper.selectById(id);
            if (student == null) {
                throw new BusinessException("学员ID " + id + " 不存在");
            }
        }

        // 2. 批量删除
        int rows = studentMapper.deleteBatch(ids);
        log.info("批量删除学员成功，共删除 {} 条记录", rows);
    }

    @Override
    public void delete(Integer id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new BusinessException("学员不存在");
        }
        int rows = studentMapper.delete(id);
        if (rows == 0) {
            throw new BusinessException("删除失败，请稍后重试");
        }
        log.info("删除学员成功,id:{}",id);
    }

    @Override
    public void update(Student student) {
        // 1. 校验学员是否存在
        Student existing = studentMapper.selectById(student.getId());
        if (existing == null) {
            throw new BusinessException("学员不存在");
        }

        // 2. 如果修改了班级，校验新班级是否存在
        if (student.getClazzId() != null && !student.getClazzId().equals(existing.getClazzId())) {
            Clazz clazz = clazzMapper.selectById(student.getClazzId());
            if (clazz == null) {
                throw new BusinessException("所属班级不存在");
            }
        }

        // 3. 如果修改了学号，校验学号是否被其他学员占用（排除自己）
        if (student.getNo() != null && !student.getNo().equals(existing.getNo())) {
            Student byNo = studentMapper.selectByNo(student.getNo());
            if (byNo != null && !byNo.getId().equals(student.getId())) {
                throw new BusinessException("学号已被其他学员占用");
            }
        }

        // 4. 如果修改了手机号，校验手机号是否被其他学员占用（排除自己）
        if (student.getPhone() != null && !student.getPhone().equals(existing.getPhone())) {
            Student byPhone = studentMapper.selectByPhone(student.getPhone());
            if (byPhone != null && !byPhone.getId().equals(student.getId())) {
                throw new BusinessException("手机号已被其他学员占用");
            }
        }

        // 5. 如果修改了身份证号，校验身份证号是否被其他学员占用（排除自己）
        if (student.getIdCard() != null && !student.getIdCard().equals(existing.getIdCard())) {
            Student byIdCard = studentMapper.selectByIdCard(student.getIdCard());
            if (byIdCard != null && !byIdCard.getId().equals(student.getId())) {
                throw new BusinessException("身份证号已被其他学员占用");
            }
        }

        // 6. 补全修改时间
        student.setUpdateTime(LocalDateTime.now());

        // 7. 执行更新（只更新非空字段）
        studentMapper.update(student);
        log.info("修改学员信息成功，id：{}", student.getId());
    }

    @Override
    public Student getById(Integer id) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new BusinessException("学员不存在");
        }
        return student;
    }

    @Override
    public void add(Student student) {
        Clazz clazz = clazzMapper.selectById(student.getClazzId());
        if (clazz == null) {
            throw new BusinessException("班级不存在");
        }
        Student existByNo = studentMapper.selectByNo(student.getNo());
        if (existByNo != null) {
            throw new BusinessException("学号已存在请勿重新添加");
        }
        Student existByPhone = studentMapper.selectByPhone(student.getPhone());
        if (existByPhone != null) {
            throw new BusinessException("手机号已被占用");
        }
        Student existByIdCard = studentMapper.selectByIdCard(student.getIdCard());
        if (existByIdCard != null) {
            throw new BusinessException("该身份证号已存在");
        }
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
        log.info("新增学员成功，ID：{}", student.getId());
    }
}