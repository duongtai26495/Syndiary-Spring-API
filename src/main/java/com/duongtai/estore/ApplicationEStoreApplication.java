package com.duongtai.estore;

import com.duongtai.estore.entities.User;
import com.duongtai.estore.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApplicationEStoreApplication {


	public static void main(String[] args) {
		SpringApplication.run(ApplicationEStoreApplication.class, args);

	}

}
