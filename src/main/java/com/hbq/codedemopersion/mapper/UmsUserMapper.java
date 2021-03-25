package com.hbq.codedemopersion.mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbq.codedemopersion.common.mapper.SuperMapper;
import com.hbq.codedemopersion.model.UmsUser;
import com.hbq.codedemopersion.vo.UmsPermissionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户表
 * 
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Mapper
public interface UmsUserMapper extends SuperMapper<UmsUser> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsUser> findList(Page<UmsUser> pages, @Param("p") Map<String, Object> params);

    List<UmsPermissionVO> selectPermissionOfUser(@Param("userId") Long userId);

    int updateToNull(Long id);
}
