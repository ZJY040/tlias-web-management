package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.globalexceptionhandler.BusinessException;
import com.itheima.mapper.ClazzMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageResult;
import com.itheima.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Clazz> findAll() {
        return clazzMapper.findAll();
    }

    @Override
    public void update(Clazz clazz) {
        // 1. 校验班级是否存在
        Clazz existing = clazzMapper.selectById(clazz.getId());
        if (existing == null) {
            throw new BusinessException("班级不存在");
        }

        // 2. 校验名称唯一性（只有传了 name 且确实改了名才需要检查）
        if (clazz.getName() != null && !clazz.getName().equals(existing.getName())) {
            // 根据新名字查询，看看是否有其他班级在用
            Clazz namecheck = clazzMapper.selectByName(clazz.getName());
            if (namecheck != null && !namecheck.getId().equals(clazz.getId())) {
                // 查到的班级不是自己 → 冲突
                throw new BusinessException("班级名称已存在，请勿重复");
            }
        }
        // 3. 补全修改时间
        clazz.setUpdateTime(LocalDateTime.now());

        // 4. 执行更新
        clazzMapper.update(clazz);
        log.info("修改班级信息成功, id: {}", clazz.getId());
    }

    @Override
    public void delete(Integer id) {
        Clazz clazz = clazzMapper.selectById(id);
        if (clazz == null) {
            throw new BusinessException("班级不存在");
        }
        int studentCount = studentMapper.countByClazzId(id);
        if (studentCount > 0) {
            throw new BusinessException("该班级下有学生不能删除");
        }
        int rows = clazzMapper.deleteById(id);
        if (rows == 0) {
            throw new BusinessException("删除失败，请稍后重试");
        }
        log.info("删除班级成功:{}",id);
    }

    @Override
    public Clazz getById(Integer id) {
        log.info("根据id查询班级信息:{}" ,id);
        return clazzMapper.selectById(id);
    }

    @Override
    public void add(Clazz clazz) {
        // 1. 补全时间字段（数据库表里的 create_time 和 update_time 允许为空，但我们主动填充）
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        // 2. (建议) 校验班级名称是否重复，防止数据库抛出 DuplicateKeyException
        // 根据 name 查询是否存在，如果存在则抛出业务异常（此处简化，直接插入，让数据库唯一索引兜底）

        // 3. 执行插入
        clazzMapper.insert(clazz);

    }

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        //1. 设置分页参数(PageHelper)
        PageHelper.startPage(clazzQueryParam.getPage(),clazzQueryParam.getPageSize());
        //2. 执行查询
        List<Clazz> clazzList = clazzMapper.selectClazzPage(clazzQueryParam);
        //3. 解析查询结果, 并封装
        PageInfo<Clazz> pageInfo = new PageInfo<>(clazzList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }
}
