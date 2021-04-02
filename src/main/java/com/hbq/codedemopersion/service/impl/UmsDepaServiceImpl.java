package com.hbq.codedemopersion.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbq.codedemopersion.mapper.UmsDepaMapper;
import com.hbq.codedemopersion.model.UmsDepa;
import com.hbq.codedemopersion.service.IUmsDepaService;
import com.hbq.codedemopersion.vo.ChildVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门
 *
 * @author hqb
 * @date 2020-09-17 14:38:50
 */
@Slf4j
@Service
public class UmsDepaServiceImpl extends ServiceImpl<UmsDepaMapper, UmsDepa> implements IUmsDepaService {
    @Resource
    private UmsDepaMapper umsDepaMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsDepa> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsDepa> pages = new Page<>(pageNum, pageSize);
        return umsDepaMapper.findList(pages, params);
    }


    @Override
    public List<String> treeUmsDepaList(List<String> childCodeList, List<UmsDepa> allList, String parentCode) {
        for (UmsDepa umsDepa : allList) {
            if (umsDepa.getParentCode()!= null){
                if (umsDepa.getParentCode().equals(parentCode)){
                    treeUmsDepaList(childCodeList,allList,umsDepa.getCode());
                    childCodeList.add(umsDepa.getCode());
                }
            }
        }
        return childCodeList;
    }

    @Override
    public List<String> getSon(Map<String, Object> params) {
        String parentCode = MapUtil.getStr(params, "depaCode");
        List<UmsDepa> allList=list();
        List<String> umsDepas = treeUmsDepaList(new ArrayList<>(),allList,parentCode);
        umsDepas.add(parentCode);
        return umsDepas;
    }

    @Override
    public String getDepaNameByCode(String depaCode) {
        if(StrUtil.isNotEmpty(depaCode)){
            UmsDepa umsDepa = getOne(new QueryWrapper<UmsDepa>().eq("code", depaCode));
            if (umsDepa != null) {
                return umsDepa.getName();
            }
        }
        return "";
    }

    @Override
    public List<String> getAllDepaBycode(List<String> allDepaName,String depaCode) {
        UmsDepa umsDepa=umsDepaMapper.getParentDepa(depaCode);
        if (umsDepa!=null && umsDepa.getParentCode()!=null){
            getAllDepaBycode(allDepaName,umsDepa.getParentCode());
            allDepaName.add(umsDepa.getName());
        }
        return allDepaName;
    }

    @Override
    public List<ChildVO> selectAllTree(String parentCode) {
        List<ChildVO> childVOS = umsDepaMapper.selectAllTree(parentCode);
        if(childVOS!=null){
            for (ChildVO childVO : childVOS) {
                    childVO.setChildVOS(selectAllTree(childVO.getCode()));
            }
        }
        return childVOS;
    }

}
