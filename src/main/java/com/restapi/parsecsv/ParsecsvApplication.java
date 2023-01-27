package com.restapi.parsecsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ParsecsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParsecsvApplication.class, args);
	}

}
