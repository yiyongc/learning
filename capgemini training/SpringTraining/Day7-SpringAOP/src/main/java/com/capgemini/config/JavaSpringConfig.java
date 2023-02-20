package com.capgemini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.capgemini.aspects.Logging;

@Configuration
@ComponentScan("com.capgemini")
@EnableAspectJAutoProxy
public class JavaSpringConfig {

	@Bean
	public Logging getLogging() {
		return new Logging();
	}
	
}
