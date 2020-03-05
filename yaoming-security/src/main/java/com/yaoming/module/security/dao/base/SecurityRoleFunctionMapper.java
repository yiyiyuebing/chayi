package com.yaoming.module.security.dao.base;

import com.yaoming.module.security.dao.base.po.SecurityRoleFunction;

public interface SecurityRoleFunctionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecurityRoleFunction record);

    int insertSelective(SecurityRoleFunction record);

    SecurityRoleFunction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecurityRoleFunction record);

    int updateByPrimaryKey(SecurityRoleFunction record);
}