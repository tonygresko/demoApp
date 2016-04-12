package com.demo.spring;

import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoAppApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(DemoAppApplication.class, args);
	}
}
