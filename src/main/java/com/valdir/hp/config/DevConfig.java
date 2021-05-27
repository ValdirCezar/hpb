package com.valdir.hp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.valdir.hp.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto=create}")
	private String ddl;

	@Bean
	public void instanciaDB() {
		if (this.ddl.equals("create")) {
			this.dbService.instanciaDB();
		}
	}
}
