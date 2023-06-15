package com.gaerine.triple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.gaerine.triple.mapper")
public class TripleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripleApplication.class, args);
	}

}
