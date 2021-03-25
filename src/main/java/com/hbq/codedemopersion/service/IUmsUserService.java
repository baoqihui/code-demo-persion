package com.hbq.codedemopersion.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.model.UmsUser;

import java.util.Map;

/**
 * 用户表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
public interface IUmsUserService extends IService<UmsUser> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsUser> findList(Map<String, Object> params);

    int updateToNull(Long id);

    Map<String, Object> getUser(String username, String password);

    Result authLogin(Map<String, Object> map);

    Result getInfo();

    Result logout();
}

