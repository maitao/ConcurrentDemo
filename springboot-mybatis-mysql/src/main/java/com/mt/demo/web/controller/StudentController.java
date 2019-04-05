package com.mt.demo.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mt.demo.web.model.Student;
import com.mt.demo.web.service.StudentService;

@RestController
@RequestMapping(value = "/student/*")
public class StudentController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private StudentService studentService;

	// 20190405 0:41 使用Autowired，需要RedisTemplate<String,
	// String>,或者不指定泛型RedisTemplate
	// 使用@Resource支持泛型指定
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 对List<E>保存，一、序列化反序列化，二、转json
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public List<Student> list() {

		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		List<Student> allStudent;
		String allStudentValue = (String) operations.get("allStudent");
		logger.info("allStudentKey:" + allStudentValue);
		if (null == allStudentValue) {
			synchronized (this) {
				allStudent = studentService.selectAll();
				String str = JSON.toJSONString(allStudent);
				operations.set("allStudent", str);

			}
		} else {
			allStudent = JSON.parseArray(allStudentValue, Student.class);
		}
		return allStudent;
	}
}
