package com.yaoming.module.security.dao.base;

import com.yaoming.module.security.dao.base.po.SecurityFunction;

public interface SecurityFunctionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecurityFunction record);

    int insertSelective(SecurityFunction record);

    SecurityFunction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecurityFunction record);

    int updateByPrimaryKey(SecurityFunction record);
}