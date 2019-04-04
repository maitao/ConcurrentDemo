package com.mt.demo.web.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mt.demo.web.model.City;

@Mapper
public interface CityMapper {
    int insert(City record);

    int insertSelective(City record);
    
}