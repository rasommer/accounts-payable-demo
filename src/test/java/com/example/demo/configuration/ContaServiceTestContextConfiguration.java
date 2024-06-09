package com.example.demo.configuration;

import org.springframework.boot.test.context.TestConfiguration;

import com.example.demo.service.ContaService;
import com.example.demo.service.impl.ContaServiceImpl;

@TestConfiguration
public class ContaServiceTestContextConfiguration {

	public ContaService contaService() {
		return new ContaServiceImpl();
	}

}
