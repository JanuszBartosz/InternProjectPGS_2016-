package com.pgs.soft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class Config {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
