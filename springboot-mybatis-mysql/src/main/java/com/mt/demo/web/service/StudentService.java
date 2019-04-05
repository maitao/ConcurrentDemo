package com.mt.demo.web.service;

import java.util.List;

import com.mt.demo.web.model.Student;

/**
 * @program: spring_boot_web
 * @description:
 * @author: gaobo
 * @create: 2018-09-20 10:17
 **/

public interface StudentService {
 List<Student> selectAll();
}
