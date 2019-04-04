package com.mt.demo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt.demo.web.mapper.CityMapper;
import com.mt.demo.web.model.City;

@Service
public class CityService {
	@Autowired
    private CityMapper cityMapper;
	
	/**
     * 
        * @Title: insertValues
        * @Description: insert city
        * @param @param city    参数
        * @return void    返回类型
        * @throws
     */
    public void insertValues(City city) {
        cityMapper.insert(city);
    }

        
}
