package com.hbq.codedemopersion.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbq.codedemopersion.common.model.PageResult;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.common.model.SysConst;
import com.hbq.codedemopersion.dto.UserRoleDTO;
import com.hbq.codedemopersion.model.UmsRole;
import com.hbq.codedemopersion.model.UmsUser;
import com.hbq.codedemopersion.model.UmsUserRole;
import com.hbq.codedemopersion.service.IUmsDepaService;
import com.hbq.codedemopersion.service.IUmsRoleService;
import com.hbq.codedemopersion.service.IUmsUserRoleService;
import com.hbq.codedemopersion.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "用户表")
@RequestMapping("/ums")
public class UmsUserController {
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private IUmsDepaService umsDepaService;
    @Autowired
    private IUmsRoleService umsRoleService;
    @Autowired
    private IUmsUserRoleService umsUserRoleService;

    /**
     * 登录
     */
    @ApiOperation(value = "登录")
    @PostMapping("/auth")
    public Result authLogin(@RequestBody Map<String, Object> map) {
        return umsUserService.authLogin(map);
    }

    /**
     * 查询当前登录用户的信息
     */
    @ApiOperation(value = "查询当前登录用户的信息")
    @PostMapping("/getInfo")
    public Result getInfo() {
        return umsUserService.getInfo();
    }


    /**
     * 登出
     */
    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public Result logout() {
        return umsUserService.logout();
    }

    /**
     * 列表
     */
    @RequiresPermissions({"user:list","user:add"})
    @ApiOperation(value = "查询列表(需要按钮list和add权限)")
    @PostMapping("/umsUser/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<UmsUser> list= umsUserService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @RequiresPermissions({"user:list"})
    @ApiOperation(value = "根据用户id查询 角色（需要用户list权限）")
    @PostMapping("/umsUser/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsUser umsUser = umsUserService.getById(id);
        List<UmsRole> roles=umsRoleService.getRoleListByUid(umsUser.getId());
        String depaCode = umsUser.getDepaCode();
        List<String> umsDepas =umsDepaService.getAllDepaBycode(new ArrayList<>(),depaCode);
        Map<String,Object> map=new HashMap<>();
        map.put("roles",roles);
        map.put("umsDepas",umsDepas);
        return Result.succeed(map, "查询成功");
    }
    /**
     * 新增
     */
    @ApiOperation(value = "新增或修改账号密码")
    @PostMapping("/umsUser/save")
    public Result save(@RequestBody UserRoleDTO userRoleDTO) {
        UmsUser umsUser = new UmsUser();
        BeanUtil.copyProperties(userRoleDTO,umsUser);
        String depaCode = umsUser.getDepaCode();
        String depaName = umsDepaService.getDepaNameByCode(depaCode);
        umsUser.setDepaName(depaName);

        List<Long> roleIds = userRoleDTO.getRoleIds();

        String userAccount = umsUser.getUserAccount();
        UmsUser existUmsUser = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", userAccount));
        String userPwd = umsUser.getUserPwd()==null? SysConst.DEFAULT_PWD:umsUser.getUserPwd();
        if (existUmsUser != null) {
            //修改角色信息
            if (roleIds != null&&roleIds.size()>0) {
                UmsUserRole umsUserRole = new UmsUserRole();
                umsUserRole.setUserId(existUmsUser.getId());
                umsUserRoleService.remove(new QueryWrapper<UmsUserRole>().eq("user_id",existUmsUser.getId()));
                for (Long roleId : roleIds) {
                    umsUserRole.setRoleId(roleId);
                    umsUserRoleService.save(umsUserRole);
                }
            }
            //修改用户部门信息
            existUmsUser.setDepaCode(depaCode);
            existUmsUser.setDepaName(depaName);
            //修改用户信息
            existUmsUser.setUserName(umsUser.getUserName());
            existUmsUser.setIsExter(umsUser.getIsExter());
            existUmsUser.setUserPwd(SecureUtil.md5(userPwd));
            umsUserService.updateById(existUmsUser);
            return Result.succeed("修改成功");
        }else {
            umsUser.setUserPwd(SecureUtil.md5(userPwd));
            umsUserService.save(umsUser);
            //保存角色信息
            if (roleIds != null&&roleIds.size()>0) {
                UmsUserRole umsUserRole = new UmsUserRole();
                umsUserRole.setUserId(umsUser.getId());
                for (Long roleId : roleIds) {
                    umsUserRole.setRoleId(roleId);
                    umsUserRoleService.save(umsUserRole);
                }
            }
            return Result.succeed("保存成功");
        }
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/umsUser/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsUserService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
}
