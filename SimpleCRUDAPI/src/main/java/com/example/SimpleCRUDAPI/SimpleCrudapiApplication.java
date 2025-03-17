package com.example.SimpleCRUDAPI;

import org.bereketab.MigrationService;
import org.bereketab.commands.MigrateCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EntityScan(basePackages = "com.example.SimpleCRUDAPI.entity")
public class SimpleCrudapiApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SimpleCrudapiApplication.class);

	@Value("${spring.datasource.url}")
	private String jdbcUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	public static void main(String[] args) {
		SpringApplication.run(SimpleCrudapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Override Migration_Tool's DataSource with Spring's settings
		logger.info("Configuring Migration_Tool with Spring DataSource");
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
		config.setDriverClassName(driverClassName);
		config.setMaximumPoolSize(10);
		HikariDataSource springDataSource = new HikariDataSource(config);

		// Use reflection to set the static dataSource field
		MigrationService migrationService = new MigrationService(springDataSource);
		migrationService.setMigrationsDir("src/main/resources/migrations");
		logger.info("Running migration command: migrate");
		new MigrateCommand(migrationService).run();
		logger.info("Migration completed");
	}
}