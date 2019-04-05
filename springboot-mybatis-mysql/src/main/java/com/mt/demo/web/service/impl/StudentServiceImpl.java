package com.mt.demo.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt.demo.web.mapper.StudentMapper;
import com.mt.demo.web.model.Student;
import com.mt.demo.web.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  private StudentMapper studentMapper;

  @Override
  public List<Student> selectAll() {
    List<Student> students = studentMapper.selectAll();
      return students;
  }
}
