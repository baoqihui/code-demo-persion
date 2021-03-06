package com.hbq.codedemopersion.mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbq.codedemopersion.common.mapper.SuperMapper;
import com.hbq.codedemopersion.model.UmsRolePer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 用户权限关联表
 * 
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Mapper
public interface UmsRolePerMapper extends SuperMapper<UmsRolePer> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsRolePer> findList(Page<UmsRolePer> pages, @Param("p") Map<String, Object> params);
}
