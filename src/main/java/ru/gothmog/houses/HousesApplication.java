package ru.gothmog.houses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class HousesApplication {
	public static void main(String[] args) {
		SpringApplication.run(HousesApplication.class, args);
	}
}
