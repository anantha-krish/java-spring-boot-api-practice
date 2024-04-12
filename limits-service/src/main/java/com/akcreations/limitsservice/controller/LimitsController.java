package com.akcreations.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akcreations.limitsservice.bean.Limit;
import com.akcreations.limitsservice.config.LimitConfiguration;

@RestController
public class LimitsController {
	@Autowired
	LimitConfiguration config;

	@GetMapping("/limits")
	public Limit getLimit() {
		return new Limit(config.getMinimum(),config.getMaximum());
	}
}
