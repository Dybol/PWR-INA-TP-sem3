package me.mikolaj;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackageClasses = Logic.class)
public class MvnRunApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MvnRunApplication.class);
		Logic logic = ctx.getBean(Logic.class);
		logic.loop();
		ctx.close();
	}

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}
}
