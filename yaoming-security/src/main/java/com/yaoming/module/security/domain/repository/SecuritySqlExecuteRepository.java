package com.yaoming.module.security.domain.repository;

import java.util.HashMap;

public interface SecuritySqlExecuteRepository {
	public int execueteSqlResultInt(String sql, HashMap<String, Object> params);
}
