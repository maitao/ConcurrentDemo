package com.mt.demo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.demo.web.model.City;
import com.mt.demo.web.service.CityService;

@RequestMapping("/city")
@RestController
public class CityController {
	@Resource
	private CityService cityService;

	@GetMapping("/insertValues")
	public Map<String, Object> insertValus() {
		Map<String, Object> map = new HashMap<String, Object>();
		City city = new City();
		city.setCityCode("370000");
		city.setCityName("山东");
		city.setId(2);
		cityService.insertValues(city);
		map.put("result", "succcess");
		return map;
	}

}
