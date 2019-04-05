package com.mt.demo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/gets")
@RestController
public class GetSession {

	@RequestMapping(value="/",method = RequestMethod.GET)
	   public Map<String,Object> getSession(HttpServletRequest request){
	       String sessionId = request.getSession().getId();
	       Object obj = request.getSession().getAttribute("userSession");
	       Map<String,Object> map = new HashMap();
	       map.put("sessionId",sessionId);
	       map.put("user",obj);
	       return map;
	   }
}
