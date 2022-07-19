package com.hbq.codedemopersion.common.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
/**
 * 车辆模型
 */
public class AutoModel {
    private Integer id;
    private String name;
}
