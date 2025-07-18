package com.blog.lastdance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LastDanceApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(LastDanceApplication.class, args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
