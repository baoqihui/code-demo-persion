package com.hbq.codedemopersion.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.model.UmsDepa;
import com.hbq.codedemopersion.vo.ChildVO;
import com.hbq.codedemopersion.vo.DepaTreeVO;

import java.util.List;
import java.util.Map;

/**
 * 部门
 *
 * @author hqb
 * @date 2020-09-17 14:38:50
 */
public interface IUmsDepaService extends IService<UmsDepa> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsDepa> findList(Map<String, Object> params);

    List<String> treeUmsDepaList(List<String> childCodeList, List<UmsDepa> allList, String parentCode);

    List<String> getSon(Map<String, Object> params);

    String getDepaNameByCode(String depaCode);

    List<String> getAllDepaBycode(List<String> allDepaName,String depaCode);

    List<ChildVO> selectAllTree(String parentCode);

    List<DepaTreeVO> selectTreeListByParentCode(String parentCode);

    Result selectListByCode(String parentCode);

    Result selectLeafListByCode(String code);

    UmsDepa selectRootDepaByCode(String code);

    List<UmsDepa> selectRootListByCode(List<UmsDepa> umsDepas,String code);

}

