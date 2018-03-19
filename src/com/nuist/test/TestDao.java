package com.nuist.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {

	@Autowired
	@Qualifier("jdbcTemplateOracle")
	private JdbcTemplate jdbcTemplate;
}
