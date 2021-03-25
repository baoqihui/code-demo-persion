package com.hbq.codedemopersion.vo;

import lombok.Data;

import java.util.List;

@Data
public class OneVO {
    private Long id;
    private String name;
    private String code;
    private Boolean status;
    private List<com.hbq.codedemopersion.vo.TwoVO> twoVOS;
}
