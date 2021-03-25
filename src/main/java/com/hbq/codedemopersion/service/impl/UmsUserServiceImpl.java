package com.hbq.codedemopersion.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.common.model.SysConst;
import com.hbq.codedemopersion.common.service.RedisService;
import com.hbq.codedemopersion.mapper.UmsUserMapper;
import com.hbq.codedemopersion.model.UmsUser;
import com.hbq.codedemopersion.service.IUmsPermissionService;
import com.hbq.codedemopersion.service.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {
    @Resource
    private UmsUserMapper umsUserMapper;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUmsPermissionService umsPermissionService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsUser> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsUser> pages = new Page<>(pageNum, pageSize);
        return umsUserMapper.findList(pages, params);
    }

    @Override
    public int updateToNull(Long id) {
        return umsUserMapper.updateToNull(id);
    }

    @Override
    public Map<String, Object> getUser(String userAccount, String password) {
        UmsUser existUmsUser = umsUserService.getOne(new LambdaQueryWrapper<UmsUser>()
                .eq(UmsUser::getUserAccount, userAccount)
                .eq(UmsUser::getUserPwd, password)
        );
        return BeanUtil.beanToMap(existUmsUser);
    }

    @Override
    public Result authLogin(Map<String, Object> map) {
        String userAccount = MapUtil.getStr(map, "userAccount");
        String userPwd = MapUtil.getStr(map, "userPwd");
        userPwd=SecureUtil.md5(userPwd);
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userAccount, userPwd);
        try {
            currentUser.login(token);
            return Result.succeed("登录成功");
        }  catch (UnknownAccountException e){
            return Result.failed("用户名或密码错误");
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return Result.failed("密码错误");
        } catch (AuthenticationException e) {
            return Result.failed("登录失败");
        }
    }

    @Override
    public Result getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        Map map =(Map)session.getAttribute(SysConst.USER_IFO);
        String userAccount = MapUtil.getStr(map,"userAccount");
        UmsUser user = umsUserService.getOne(new LambdaQueryWrapper<UmsUser>().eq(UmsUser::getUserAccount, userAccount));
        Map<String, Object> userMap = BeanUtil.beanToMap(user);
        List<Map<String, Object>> userAllPermission = umsPermissionService.getUserPermission(userAccount);
        List<String> userButtonPermission = umsPermissionService.getUserButtonPermission(userAccount);
        userMap.put("userAllPermission",userAllPermission);
        userMap.put("userButtonPermission",userButtonPermission);
        session.setAttribute(SysConst.SESSION_USER_PERMISSION, userMap);
        return Result.succeed(userMap,"成功");
    }

    @Override
    public Result logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return Result.succeed("退出成功");
    }
}
