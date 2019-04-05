package com.mt.demo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sets")
@RestController
public class SetSession {
	@RequestMapping(value="/",method = RequestMethod.GET)
	   public String setSession(HttpServletRequest request){
	       Map<String,Object> map = new HashMap();
	       map.put("name","超级管理员");
	       map.put("account","admin");
	       request.getSession().setAttribute("userSession",map);
	       String sessionId = request.getSession().getId();
	       return sessionId;
	   }
}
