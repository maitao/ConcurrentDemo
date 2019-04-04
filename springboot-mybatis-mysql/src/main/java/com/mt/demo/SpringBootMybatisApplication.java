package com.mt.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisApplication.class, args);
	}

}
