package com.yaoming.module.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yaoming.module.security.dao.base.SecurityGlobalResourcePatternMapper;
import com.yaoming.module.security.dao.base.po.SecurityGlobalResourcePattern;

public interface SecurityGlobalResourcePatternExtendMapper extends SecurityGlobalResourcePatternMapper {
	public List<SecurityGlobalResourcePattern> selectAll();
	
	public void replace(SecurityGlobalResourcePattern record);
	
	public void updateByOldId(
			@Param("id") long id,
			@Param("oldId") long oldId,
			@Param("pattern") String pattern,
			@Param("authority") String authority,
			@Param("note") String note);
}