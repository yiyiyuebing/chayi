package com.yaoming.module.security.dao.base;

import com.yaoming.module.security.dao.base.po.SecurityRoleUser;

public interface SecurityRoleUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecurityRoleUser record);

    int insertSelective(SecurityRoleUser record);

    SecurityRoleUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecurityRoleUser record);

    int updateByPrimaryKey(SecurityRoleUser record);
}