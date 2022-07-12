package com.hbq.codedemopersion.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DingDingBot {
    private String timestamp;
    private String sign;
}
