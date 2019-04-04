package com.mt.test.web.domain;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

	@Select("SELECT * FROM [user] WHERE name = #{name}")
	User findByName(@Param("name") String name);

	@Insert("insert into [user](user_id, name, age, host) values (#{userId},  #{name}, #{age}, #{host})")
	int insert(@Param("userId") String userId, @Param("name") String name, @Param("age") Integer age,
			@Param("host") String host);

}