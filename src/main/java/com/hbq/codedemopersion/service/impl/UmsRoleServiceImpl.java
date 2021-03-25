package com.hbq.codedemopersion.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.dto.RoleInsertDTO;
import com.hbq.codedemopersion.mapper.UmsRoleMapper;
import com.hbq.codedemopersion.mapper.UmsRolePerMapper;
import com.hbq.codedemopersion.model.UmsRole;
import com.hbq.codedemopersion.model.UmsRolePer;
import com.hbq.codedemopersion.model.UmsUser;
import com.hbq.codedemopersion.service.IUmsRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 * @author hqb
 * @date 2020-09-12 16:38:04
 */
@Slf4j
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements IUmsRoleService {
    @Resource
    private UmsRoleMapper umsRoleMapper;
    @Resource
    private UmsRolePerMapper umsRolePerMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<Map> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsRole> pages = new Page<>(pageNum, pageSize);
        return umsRoleMapper.findList(pages, params);
    }
    @Override
    public Result insertRole(RoleInsertDTO roleInsertDTO) {
        UmsRole umsRole=new UmsRole();
        BeanUtil.copyProperties(roleInsertDTO,umsRole);
        umsRoleMapper.insert(umsRole);
        Long roleId = umsRole.getId();
        List<Long> perIdList = roleInsertDTO.getPerIdList();
        UmsRolePer umsRolePer=new UmsRolePer();
        umsRolePer.setRoleId(roleId);
        for (Long perId : perIdList) {
            umsRolePer.setPerId(perId);
            umsRolePerMapper.insert(umsRolePer);
        }
        return Result.succeed("添加成功");
    }

    @Override
    public Result editRole(RoleInsertDTO roleInsertDTO) {
        UmsRole umsRole=new UmsRole();
        BeanUtil.copyProperties(roleInsertDTO,umsRole);
        umsRoleMapper.updateById(umsRole);
        Long roleId = umsRole.getId();
        umsRolePerMapper.delete(new QueryWrapper<UmsRolePer>().eq("role_id",roleId));
        List<Long> perIdList = roleInsertDTO.getPerIdList();
        UmsRolePer umsRolePer=new UmsRolePer();
        umsRolePer.setRoleId(roleId);
        for (Long perId : perIdList) {
            umsRolePer.setPerId(perId);
            umsRolePerMapper.insert(umsRolePer);
        }
        return Result.succeed("修改成功");
    }

    @Override
    public Result getUserByRoleId(Map<String, Object> map) {
        Long roleId = MapUtil.getLong(map, "roleId");
        List<UmsUser> roleList=umsRoleMapper.getUserByRoleId(roleId);
        List<UmsUser> noRoleList=umsRoleMapper.getNoRoleUserByRoleId(roleId);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("roleList",roleList);
        objectMap.put("noRoleList",noRoleList);
        return Result.succeed(objectMap,"查询成功");
    }

    @Override
    public List<UmsRole>  getRoleListByUid(Long id) {
        return umsRoleMapper.getRoleListByUid(id);
    }
}
