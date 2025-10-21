package lab.is;

import org.springframework.boot.SpringApplication;

public class TestIsApplication {

	public static void main(String[] args) {
		SpringApplication.from(IsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
