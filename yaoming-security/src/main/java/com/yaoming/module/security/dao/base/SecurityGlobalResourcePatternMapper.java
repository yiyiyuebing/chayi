package com.yaoming.module.security.dao.base;

import com.yaoming.module.security.dao.base.po.SecurityGlobalResourcePattern;

public interface SecurityGlobalResourcePatternMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecurityGlobalResourcePattern record);

    int insertSelective(SecurityGlobalResourcePattern record);

    SecurityGlobalResourcePattern selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecurityGlobalResourcePattern record);

    int updateByPrimaryKey(SecurityGlobalResourcePattern record);
}