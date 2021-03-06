package com.hbq.codedemopersion.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbq.codedemopersion.model.UmsUserRole;

import java.util.Map;

/**
 * 用户角色关联表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
public interface IUmsUserRoleService extends IService<UmsUserRole> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsUserRole> findList(Map<String, Object> params);
}

