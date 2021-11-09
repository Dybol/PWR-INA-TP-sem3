package me.mikolaj;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackageClasses = Logic.class)
public class MvnRunApplication {

	//klasa glowna, tu uruchamiamy nasza aplikacje
	public static void main(final String[] args) {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MvnRunApplication.class);
		final Logic logic = ctx.getBean(Logic.class);
		logic.loop();
		ctx.close();
	}

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}
}
