package com.nuist.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuist.ecm.dao.login.LoginDao;

@Service
public class TestService {

	@Autowired
	private LoginDao logindao;
	
	public Map<String, Object> checkName(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isNameExists = logindao.checkloginname(name);
		result.put("isUnique", isNameExists);
		return result;
	}
}
