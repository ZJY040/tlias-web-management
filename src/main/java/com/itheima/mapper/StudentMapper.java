package com.itheima.mapper;

import com.itheima.dto.StatisticsVO;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    int countByClazzId(Integer clazzId);

    List<Student> selectStudentpage(StudentQueryParam studentQueryParam);

    void insert(Student student);

    // 根据学号查询
    Student selectByNo(String no);

    // 根据手机号查询
    Student selectByPhone(String phone);

    Student selectByIdCard(String idCard);

    Student getById(Integer id);

    void update(Student student);

    Student selectById(Integer id);

    int delete(Integer id);

    int deleteBatch(List<Integer> ids);

    void updateViolation(Integer id, Integer newViolationCount, Integer newViolationScore);

    List<StatisticsVO> countStudentByDegree();
}
