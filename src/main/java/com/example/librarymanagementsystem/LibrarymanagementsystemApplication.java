package com.example.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrarymanagementsystemApplication {


	public static void main(String[] args) {
		try {
			SpringApplication.run(LibrarymanagementsystemApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
