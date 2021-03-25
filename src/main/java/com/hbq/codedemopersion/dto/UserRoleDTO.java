package com.hbq.codedemopersion.dto;

import com.hbq.codedemopersion.model.UmsUser;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleDTO extends UmsUser {
   private List<Long> roleIds;
}
