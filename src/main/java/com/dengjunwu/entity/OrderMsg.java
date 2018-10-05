package com.dengjunwu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dengjunwu on 2018/10/4.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMsg {
    private Long id;

    private String name;

    private String description;

    private Double amount;

    private Long createTime;

    private Long updateTime;
}
