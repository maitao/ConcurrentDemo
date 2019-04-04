package com.mt.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mt.test.web.domain.UserMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ApplicationTests {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private UserMapper userMapper;

	@Test
	@Rollback
	public void findByName() throws Exception {
		logger.info("插入数据");
		userMapper.insert("1", "hhh", 20, "");
		userMapper.insert("2", "BBB", 20, "");
		userMapper.insert("3", "CCC", 0, "");

//		User u = userMapper.findByName("AAA");
//		Assert.assertEquals(20, u.getAge().intValue());
	}

}