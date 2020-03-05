package com.yaoming.module.security.dao.base;

import com.yaoming.module.security.dao.base.po.SecurityFunctionResourcePattern;

public interface SecurityFunctionResourcePatternMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecurityFunctionResourcePattern record);

    int insertSelective(SecurityFunctionResourcePattern record);

    SecurityFunctionResourcePattern selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecurityFunctionResourcePattern record);

    int updateByPrimaryKey(SecurityFunctionResourcePattern record);
}