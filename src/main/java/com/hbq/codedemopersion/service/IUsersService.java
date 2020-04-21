package com.hbq.codedemopersion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbq.codedemopersion.common.model.PageResult;
import com.hbq.codedemopersion.model.Users;

import java.util.Map;

/**
 * 账号信息
 *
 * @author hqb
 * @date 2020-03-10 09:40:54
 */
public interface IUsersService extends IService<Users> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<Users> findList(Map<String, Object> params);
}

