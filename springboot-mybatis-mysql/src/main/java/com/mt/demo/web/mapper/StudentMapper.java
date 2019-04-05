package com.mt.demo.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mt.demo.web.model.Student;

@Mapper
public interface StudentMapper {

  List<Student> selectAll();
}
