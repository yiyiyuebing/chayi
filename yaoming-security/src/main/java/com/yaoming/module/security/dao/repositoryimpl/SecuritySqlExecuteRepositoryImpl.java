package com.yaoming.module.security.dao.repositoryimpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yaoming.module.security.dao.SecuritySqlExecuteMapper;
import com.yaoming.module.security.domain.repository.SecuritySqlExecuteRepository;

@Repository
public class SecuritySqlExecuteRepositoryImpl implements SecuritySqlExecuteRepository {

	@Autowired private SecuritySqlExecuteMapper sqlExecuteDao;
	
	@Override
	public int execueteSqlResultInt(String sql, HashMap<String, Object> params) {
		params.put("sql", sql);
		return sqlExecuteDao.selectResultInt(params);
	}

}
