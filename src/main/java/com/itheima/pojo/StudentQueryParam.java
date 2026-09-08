package com.itheima.pojo;

import lombok.Data;

@Data
public class StudentQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;

    // 查询条件
    private String name;        // 学员姓名（模糊）
    private String no;          // 学号（模糊）
    private String phone;       // 手机号（模糊）
    private Integer clazzId;    // 所属班级ID（精确，下拉框选中的值）
    private Integer gender;
}
