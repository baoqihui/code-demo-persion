package com.hbq.codedemopersion.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbq.codedemopersion.common.model.PageResult;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.model.UmsDepa;
import com.hbq.codedemopersion.model.UmsUser;
import com.hbq.codedemopersion.service.IUmsDepaService;
import com.hbq.codedemopersion.vo.ChildVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门
 *
 * @author hqb
 * @date 2020-09-17 14:38:50
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "部门")
@RequestMapping("ums")
public class UmsDepaController {
    @Autowired
    private IUmsDepaService umsDepaService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsDepa/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsDepa> list= umsDepaService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    @ApiOperation(value = "部门员工列表")
    @PostMapping("/umsDepa/getUserByDepaName")
    public Result getUserByDepaName(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);

        List<UmsUser> depaUsers=umsDepaService.getUserByDepaName(params);
        List<UmsUser> noDepaUsers=umsDepaService.getNoUserByDepaName(params);
        Map<String, Object> result = new HashMap<>();
        result.put("depaUsers",depaUsers);
        result.put("noDepaUsers",noDepaUsers);
        return Result.succeed(result,"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsDepa/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsDepa model = umsDepaService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping("/umsDepa/save")
    public Result save(@RequestBody UmsDepa umsDepa) {
        umsDepaService.saveOrUpdate(umsDepa);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/umsDepa/del/{id}")
    public Result delete(@PathVariable Long id) {
        umsDepaService.removeById(id);
        return Result.succeed("删除成功");
    }

    @ApiOperation(value = "（递归）通过code查询所有并列子级")
    @PostMapping("/umsDepa/getSon")
    public Result getSon(@RequestBody Map<String, Object> params) {
        return Result.succeed(umsDepaService.getSon(params));
    }

    /**
     * 通过parentCode递归查询树状部门列表 parentCode='0'查询全部
     */
    @ApiOperation(value = "（递归）通过parentCode递归查询树状部门列表 parentCode='0'查询全部")
    @PostMapping("/umsDepa/depaTree/{parentCode}")
    public Result selectAllTree(@PathVariable String parentCode) {
        List<ChildVO> childVOS = umsDepaService.selectAllTree(parentCode);
        return Result.succeed(childVOS, "查询成功");
    }

    /*@ApiOperation(value = "(三级)查询树形列表（所有）")
    @PostMapping("/umsDepa/treeList")
    public Result treeList() {
        List<OneVO> depaOneVOS=umsDepaService.treeList();
        return Result.succeed(depaOneVOS,"查询成功");
    }
    @ApiOperation(value = "（三级）查询树形列表（仅机构）")
    @PostMapping("/umsDepa/treeOrgList")
    public Result treeOrgList() {
        List<TwoVO> depaTwoVOS=umsDepaService.treeOrgList();
        return Result.succeed(depaTwoVOS,"查询成功");
    }
    @ApiOperation(value = "未分配-》已分配")
    @PostMapping("/umsUserDepa/saveUserdepa")
    public Result saveUserRole(@RequestBody Map<String,List<UmsUser>> map) {
        List<UmsUser> umsUsers=map.get("users");
        for (UmsUser umsUser : umsUsers) {
            Long id = umsUser.getId();
            String depaCode = umsUser.getDepaCode();
            String depaName=umsDepaService.getDepaNameByCode(depaCode);
            UmsUser existUser = umsUserService.getOne(new QueryWrapper<UmsUser>()
                    .eq("id",id)
                    .eq("is_del",0));
            if (existUser != null) {
                existUser.setDepaCode(depaCode);
                existUser.setDepaName(depaName);
                umsUserService.updateById(existUser);
            }

        }
        return Result.succeed("修改成功");
    }

    @ApiOperation(value = "已分配-》未分配")
    @PostMapping("/umsUserDepa/remove")
    public Result editUserRole(@RequestBody Map<String,List<UmsUser>> map) {
        List<UmsUser> umsUsers=map.get("users");
        for (UmsUser umsUser : umsUsers) {
            Long id = umsUser.getId();
            int i =umsUserService.updateToNull(id);
        }
        return Result.succeed("移除成功");
    }*/
}
