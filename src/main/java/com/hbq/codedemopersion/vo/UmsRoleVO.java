package com.hbq.codedemopersion.vo;

import com.hbq.codedemopersion.model.UmsRole;
import lombok.Data;

import java.util.List;

@Data
public class UmsRoleVO extends UmsRole {
    /** 角色权限关系 */
    private List<Long> perIds;
}
