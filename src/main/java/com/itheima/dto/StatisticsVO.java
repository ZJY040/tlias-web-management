package com.itheima.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsVO {
    private String name;   // 学历名称，如"本科"
    private Long value;    // 对应人数
}