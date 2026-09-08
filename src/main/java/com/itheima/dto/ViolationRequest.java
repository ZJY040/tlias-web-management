package com.itheima.dto;

import lombok.Data;

@Data
public class ViolationRequest {
    private Integer score;  // 本次违纪扣分（正整数）
}