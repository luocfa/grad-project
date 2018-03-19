package com.nuist.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.nuist.common.utils.SpringMVCResult;

@RequestMapping("/test")
@Controller
public class TestAction {

	@Autowired
	private TestService testService;
	
	@RequestMapping("index.htm")
	public ModelAndView index() {
		return new ModelAndView("/WEB-INF/view/angularJs/index.html");
	}
	
	@RequestMapping("checkname.htm")
	public ResponseEntity<String> checkUsername(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "value", required = false) String value) {
		Map<String, Object> result = testService.checkName(name);
		return SpringMVCResult.returnResponseEntity(JSON.toJSONString(result));
	}
	
}
