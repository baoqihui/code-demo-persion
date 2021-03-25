package com.hbq.codedemopersion.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hbq.codedemopersion.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_permission")
public class UmsPermission extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "权限名")
        @ApiModelProperty(value = "权限名")
        private String perName;
        @Excel(name = "权限路径")
        @ApiModelProperty(value = "权限路径")
        private String perUrl;
        @Excel(name = "图标")
        @ApiModelProperty(value = "图标")
        private String icon;
        @Excel(name = "排序")
        @ApiModelProperty(value = "排序")
        private Boolean perSort;
        @Excel(name = "权限类型 例如 0 菜单 1按钮")
        @ApiModelProperty(value = "权限类型 例如 0 菜单 1按钮")
        private Boolean perType;
        @Excel(name = "一级权限 默认 0 ")
        @ApiModelProperty(value = "一级权限 默认 0 ")
        private Long parentId;
        @Excel(name = "状态 0 禁用 1 启用")
        @ApiModelProperty(value = "状态 0 禁用 1 启用")
        private Boolean status;
        @Excel(name = "描述")
        @ApiModelProperty(value = "描述")
        private String remark;
}
