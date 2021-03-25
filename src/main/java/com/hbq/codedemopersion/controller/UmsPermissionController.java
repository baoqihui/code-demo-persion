package com.hbq.codedemopersion.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbq.codedemopersion.common.model.PageResult;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.model.UmsPermission;
import com.hbq.codedemopersion.service.IUmsPermissionService;
import com.hbq.codedemopersion.vo.PermissionTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 权限表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "权限表")
@RequestMapping("/ums")
public class UmsPermissionController {
    @Autowired
    private IUmsPermissionService umsPermissionService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsPermission/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsPermission> list = umsPermissionService.findList(params);
        return Result.succeed(PageResult.restPage(list), "查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsPermission/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsPermission model = umsPermissionService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping("/umsPermission/save")
    public Result save(@RequestBody UmsPermission umsPermission) {
        umsPermissionService.saveOrUpdate(umsPermission);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/umsPermission/del/{id}")
    public Result delete(@PathVariable Long id) {
        umsPermissionService.removeById(id);
        return Result.succeed("删除成功");
    }

    /**
     * 通过parentId递归查询权限树（查询全部传0）
     */
    @ApiOperation(value = "通过parentId递归查询权限树（查询全部传0）")
    @PostMapping("/umsPermission/selectPermissionTreeByParentId/{parentId}")
    public Result selectPermissionTreeByParentId(@PathVariable Long parentId) {
        List<PermissionTreeVO> permissionTreeVOS = umsPermissionService.selectPermissionTreeByParentId(parentId);
        return Result.succeed(permissionTreeVOS, "查询成功");
    }
}