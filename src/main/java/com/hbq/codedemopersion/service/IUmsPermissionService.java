package com.hbq.codedemopersion.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.model.UmsPermission;
import com.hbq.codedemopersion.vo.PermissionTreeVO;

import java.util.List;
import java.util.Map;

/**
 * 权限表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
public interface IUmsPermissionService extends IService<UmsPermission> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsPermission> findList(Map<String, Object> params);

    List<PermissionTreeVO> selectPermissionTreeByParentId(Long parentId);

    List<PermissionTreeVO> getUserPermission(String userAccount, Long parentId);

    List<String> getUserButtonPermission(String userAccount);

    Result delete(Long id);
}

