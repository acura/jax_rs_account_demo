package com.org.configuration;

import java.io.File;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsondb.JsonDBTemplate;

@Configuration
public class AppConfig {

	@Bean
	public JsonDBTemplate jsonDBTemplate() {
		return new JsonDBTemplate( getDbPath() + File.separator + "accounts", "com.org.model");
	}
	
	/**
	 * Returns db path from environment variable if not found then return default user.dir path
	 * @return
	 */
	private String getDbPath() {
		String dbPath = System.getProperty("user.dir");
		if(System.getenv("db.path")!=null && !System.getenv("db.path").isEmpty()) {
			dbPath = System.getenv("db.path");
		}
		return dbPath;
	}
}
