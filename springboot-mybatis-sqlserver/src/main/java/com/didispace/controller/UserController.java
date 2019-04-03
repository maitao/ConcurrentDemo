package com.didispace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.didispace.domain.UserMapper;

@RestController
public class UserController {

	@Autowired
	private UserMapper userMapper;

	@Value("${server.port}")
	private String port;

	@RequestMapping("/insertValues")
	public String insertValus(@RequestParam String userId) {
		userMapper.insert(userId, "AAA", 20, "localhost:" + port);
		return "Hello World";
	}

}
