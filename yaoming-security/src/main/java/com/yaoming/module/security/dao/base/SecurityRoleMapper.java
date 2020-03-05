package com.yaoming.module.security.dao.base;

import com.yaoming.module.security.dao.base.po.SecurityRole;

public interface SecurityRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecurityRole record);

    int insertSelective(SecurityRole record);

    SecurityRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecurityRole record);

    int updateByPrimaryKey(SecurityRole record);
}