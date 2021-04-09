package com.hbq.codedemopersion.vo;

import com.hbq.codedemopersion.model.UmsDepa;
import lombok.Data;

import java.util.List;

@Data
public class DepaTreeVO extends UmsDepa {
    //存放子级集合
    private List<DepaTreeVO> children;
}
