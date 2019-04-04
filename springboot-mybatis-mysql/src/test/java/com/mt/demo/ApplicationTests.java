package com.mt.demo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.mt.demo.web.domain.User;
import com.mt.demo.web.domain.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CacheManager cacheManager;

	@Before
	public void before() {
		userRepository.save(new User("OOO", 10));
	}

	@Test
	public void test() throws Exception {

		User u1 = userRepository.findByName("UUU");
		System.out.println("第一次查询：" + u1.getAge());

		User u2 = userRepository.findByName("UUU");
		System.out.println("第二次查询：" + u2.getAge());

		u1.setAge(20);
//		userRepository.save(u1);
		User u3 = userRepository.findByName("UUU");
		System.out.println("第三次查询：" + u3.getAge());

	}

}
