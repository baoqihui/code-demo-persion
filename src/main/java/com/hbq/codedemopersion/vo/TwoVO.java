package com.hbq.codedemopersion.vo;

import lombok.Data;

import java.util.List;

@Data
public class TwoVO {
    private Long id;
    private String name;
    private String code;
    private Boolean status;
    private List<com.hbq.codedemopersion.vo.ThrVO> thrVOS;
}
