package com.yaoming.module.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yaoming.module.security.dao.base.SecurityFunctionResourcePatternMapper;
import com.yaoming.module.security.dao.base.po.SecurityFunctionResourcePattern;

public interface SecurityFunctionResourcePatternExtendMapper extends SecurityFunctionResourcePatternMapper {
	public List<SecurityFunctionResourcePattern> selectAllFunctionResources();
	public List<SecurityFunctionResourcePattern> selectByFunctionId(long functionId);
	
	public void replace(SecurityFunctionResourcePattern record);
	public void updateByOldId(
			@Param("id") long id,
			@Param("oldId") long oldId,
			@Param("functionId") long functionId,
			@Param("pattern") String pattern,
			@Param("note") String note);
}