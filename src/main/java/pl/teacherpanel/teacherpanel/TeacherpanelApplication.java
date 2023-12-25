package pl.teacherpanel.teacherpanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TeacherpanelApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherpanelApplication.class, args);
	}
	@GetMapping()
	public String sayHello() {
		return "Teacher Panel";
	}
}