package com.example.shax_app;

import org.springframework.boot.SpringApplication;

public class TestShaxAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(ShaxAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
