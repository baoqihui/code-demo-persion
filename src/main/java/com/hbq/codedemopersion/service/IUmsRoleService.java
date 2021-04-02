package com.hbq.codedemopersion.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.dto.RoleInsertDTO;
import com.hbq.codedemopersion.model.UmsRole;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
public interface IUmsRoleService extends IService<UmsRole> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result insertRole(RoleInsertDTO roleInsertDTO);

    Result editRole(RoleInsertDTO roleInsertDTO);

    List<UmsRole>  getRoleListByUid(Long id);

    Result delete(Long id);
}

