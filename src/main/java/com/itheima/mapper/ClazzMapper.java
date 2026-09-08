package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ClazzMapper {
    List<Clazz> findAll();

    List<Clazz> selectClazzPage(ClazzQueryParam clazzQueryParam);

    void insert(Clazz clazz);

    Clazz selectById(Integer id);

    Clazz selectByName(String name);

    void update(Clazz clazz);

    int deleteById(Integer id);
}
