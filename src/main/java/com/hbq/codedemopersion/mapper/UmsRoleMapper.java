package com.hbq.codedemopersion.mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbq.codedemopersion.common.mapper.SuperMapper;
import com.hbq.codedemopersion.model.UmsRole;
import com.hbq.codedemopersion.model.UmsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 * 
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Mapper
public interface UmsRoleMapper extends SuperMapper<UmsRole> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<UmsRole> pages, @Param("p") Map<String, Object> params);

    @Select("SELECT * FROM ums_user s WHERE s.id in (SELECT r.user_id FROM ums_user_role r WHERE r.role_id=#{roleId})")
    List<UmsUser> getUserByRoleId(@Param("roleId")Long roleId);

    @Select("SELECT * FROM ums_user s WHERE s.id not in (SELECT r.user_id FROM ums_user_role r WHERE r.role_id=#{roleId})")
    List<UmsUser> getNoRoleUserByRoleId( @Param("roleId")Long roleId);

    List<UmsRole> getRoleListByUid(@Param("UserId") Long id);
}
