package com.yaoming.module.security.service;

import java.util.List;

import com.yaoming.module.security.service.vo.SecurityTreeNodeVO.SecurityFunctionTreeNodeVO;

public interface SecurityFunctionService {
	public static final String SKIP_BODY = "SKIP_BODY";
	public static final String EVAL_PAGE = "EVAL_PAGE";
	
	public static final String DEFAULT_USER_TABLE_NAME = "user";
	public static final String DEFAULT_USERID_FIELD_NAME = "id";
	public static final String DEFAULT_USERNAME_FIELD_NAME = "username";
	
	public static final String FUNCTION_NAME_PARAM = "#{functionname}";
	
	default public String getDefaultBodyAttr(){
		return SKIP_BODY;
	}
	
	default public String getUserTableName(){
		return DEFAULT_USER_TABLE_NAME;
	}
	
	default public String getUseridFieldName(){
		return DEFAULT_USERID_FIELD_NAME;
	}
	
	default public String getUsernameFieldName(){
		return DEFAULT_USERNAME_FIELD_NAME;
	}
	
	default public String getUserFunctionAuthoritySql(){
		return getDefaultUserFunctionAuthoritySql();
	}
	
	default public String getDefaultUserFunctionAuthoritySql(){
		StringBuilder sb = new StringBuilder()
				.append("select * from _security_role_function a ")
				.append("left join _security_function b on a.function_id=b.id ")
				.append("where b.name in('#{functionname}') ")
				.append("and a.role_id in (")
				.append("select c.role_id from _security_role_user c ")
				.append("left join `").append(getUserTableName()).append("` d on c.user_id=d.`").append(getUseridFieldName()).append("` ")
				.append("where d.`").append(getUsernameFieldName()).append("`=#{username});");
		return sb.toString();
	}
	
	public boolean isUserHasFunctionAuthority(String username, String functionname);

	public List<SecurityFunctionTreeNodeVO> getRoleFunctions(String... roleNames);
}
