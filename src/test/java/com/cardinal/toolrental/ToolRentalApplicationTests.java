package com.cardinal.toolrental;


import com.cardinal.toolrental.controllers.ToolController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ToolRentalApplicationTests {

	@Autowired
	private ToolController toolController;

	RestTemplate restTemplate = new RestTemplate();

	@Test
	void contextLoads() throws Exception {
		assertThat(toolController).isNotNull();
	}



}
