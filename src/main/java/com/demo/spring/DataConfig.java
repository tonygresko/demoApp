package com.demo.spring;

import oracle.jdbc.pool.OracleDataSource;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataConfig {
	
	public DataSource getDataSource(String username, String password, String url) throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setURL(url);
		//dataSource.setImplicitCachingEnabled(true);
		//dataSource.setFastConnectionFailoverEnabled(true);

		return dataSource;
	}
}
