package com.hbq.codedemopersion.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbq.codedemopersion.common.model.PageResult;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.model.UmsDepa;
import com.hbq.codedemopersion.service.IUmsDepaService;
import com.hbq.codedemopersion.vo.ChildVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 通过parentCode递归查询树状部门列表 parentCode='0'查询全部
     */
    @ApiOperation(value = "（递归）通过parentCode递归查询树状部门列表 parentCode='0'查询全部")
    @PostMapping("/umsDepa/depaTree/{parentCode}")
    public Result selectAllTree(@PathVariable String parentCode) {
        List<ChildVO> childVOS = umsDepaService.selectAllTree(parentCode);
        return Result.succeed(childVOS, "查询成功");
    }
}
