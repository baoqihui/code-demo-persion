package com.hbq.codedemopersion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.mapper.UmsPermissionMapper;
import com.hbq.codedemopersion.model.UmsPermission;
import com.hbq.codedemopersion.model.UmsRolePer;
import com.hbq.codedemopersion.service.IUmsPermissionService;
import com.hbq.codedemopersion.service.IUmsRolePerService;
import com.hbq.codedemopersion.vo.PermissionTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@Service
public class UmsPermissionServiceImpl extends ServiceImpl<UmsPermissionMapper, UmsPermission> implements IUmsPermissionService {
    @Resource
    private UmsPermissionMapper umsPermissionMapper;
    @Autowired
    private IUmsRolePerService umsRolePerService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsPermission> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsPermission> pages = new Page<>(pageNum, pageSize);
        return umsPermissionMapper.findList(pages, params);
    }
    /**
     * @Description: 以输入的父级id寻找下级目录，只要下级目录不为空，就继续向下探寻，然后封装至上级的childs字段中
     * @param parentId
     * @return
     */
    @Override
    public List<PermissionTreeVO> selectPermissionTreeByParentId(Long parentId) {
        List<PermissionTreeVO> permissionList = umsPermissionMapper.selectPermissionTreeByParentId(parentId);
        if(permissionList!=null){
            for (PermissionTreeVO permission : permissionList) {
                permission.setChildren(selectPermissionTreeByParentId(permission.getId()));
            }
        }
        return permissionList;
    }

    @Override
    public List<PermissionTreeVO> getUserPermission(String userAccount,Long parentId) {
        List<PermissionTreeVO> permissionList = umsPermissionMapper.getUserPermission(userAccount,parentId);
        if(permissionList!=null){
            for (PermissionTreeVO permission : permissionList) {
                List<PermissionTreeVO> childrens = getUserPermission(userAccount, permission.getId());
                /**
                 * 因为同一用户可能有多个角色，角色的权限可能相同 应对子级去重
                 * */
                childrens= childrens.stream().distinct().collect(Collectors.toList());
                permission.setChildren(childrens);
            }
        }
        /**
         * 因为同一用户可能有多个角色，角色的权限可能相同 最后对父级去重
         * */
        permissionList= permissionList.stream().distinct().collect(Collectors.toList());
        return permissionList;
    }

    @Override
    public List<String>  getUserButtonPermission(String userAccount) {
        return umsPermissionMapper.getUserButtonPermission(userAccount);
    }

    @Override
    public Result delete(Long id) {
        List<UmsRolePer> umsRolePers = umsRolePerService.list(new LambdaQueryWrapper<UmsRolePer>()
                .eq(UmsRolePer::getPerId, id)
        );
        if (!umsRolePers.isEmpty()) {
            return Result.failed("请先解绑该权限绑定的角色");
        }
        removeById(id);
        return Result.succeed("删除成功");
    }
}
