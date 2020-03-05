package com.yaoming.module.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaoming.common.util.StringUtil;
import com.yaoming.module.security.domain.SecurityFunctionTree;
import com.yaoming.module.security.domain.SecurityLoader;
import com.yaoming.module.security.domain.repository.SecuritySqlExecuteRepository;
import com.yaoming.module.security.service.SecurityFunctionService;
import com.yaoming.module.security.service.vo.SecurityTreeNodeVO.SecurityFunctionTreeNodeVO;

@Service("defaultFunctionService")
public class DefaultSecurityFunctionServiceImpl implements SecurityFunctionService {
	
	private String userTableName;
	private String useridFieldName;
	private String usernameFieldName;
	
	private String userFunctionAuthoritySql;
	
	@Autowired private SecuritySqlExecuteRepository sqlExecuteRepository;
	@Autowired private SecurityLoader securityLoader;
	
	@Override
	public boolean isUserHasFunctionAuthority(String username, String functionname) {
		String sql = getUserFunctionAuthoritySql().replace(FUNCTION_NAME_PARAM, functionname.replaceAll("\\s+", "").replace(",", "','"));
		HashMap<String, Object> params = new HashMap<>();
		params.put("username", username);
		return sqlExecuteRepository.execueteSqlResultInt(sql, params)>0;
	}

	public String getUserTableName() {
		if(StringUtil.isEmpty(userTableName))
			userTableName = DEFAULT_USER_TABLE_NAME;
		return userTableName;
	}

	public void setUserTableName(String userTableName) {
		this.userTableName = userTableName;
	}

	public String getUseridFieldName() {
		if(StringUtil.isEmpty(useridFieldName))
			useridFieldName = DEFAULT_USERID_FIELD_NAME;
		return useridFieldName;
	}

	public void setUseridFieldName(String useridFieldName) {
		this.useridFieldName = useridFieldName;
	}

	public String getUsernameFieldName() {
		if(StringUtil.isEmpty(usernameFieldName))
			usernameFieldName = DEFAULT_USERNAME_FIELD_NAME;
		return usernameFieldName;
	}

	public void setUsernameFieldName(String usernameFieldName) {
		this.usernameFieldName = usernameFieldName;
	}

	public String getUserFunctionAuthoritySql() {
		if(StringUtil.isEmpty(userFunctionAuthoritySql))
			userFunctionAuthoritySql = getDefaultUserFunctionAuthoritySql();
		return userFunctionAuthoritySql;
	}

	public void setUserFunctionAuthoritySql(String userFunctionAuthoritySql) {
		this.userFunctionAuthoritySql = userFunctionAuthoritySql;
	}
	
	@Override
	public List<SecurityFunctionTreeNodeVO> getRoleFunctions(String... roleNames) {
		List<SecurityFunctionTreeNodeVO> result = new ArrayList<>();
		List<SecurityFunctionTree> doList = securityLoader.getRoleFunctions(roleNames);
		for (SecurityFunctionTree tree : doList)
			result.add(SecurityFunctionTreeNodeVO.create(tree));
		return result;
	}
}
