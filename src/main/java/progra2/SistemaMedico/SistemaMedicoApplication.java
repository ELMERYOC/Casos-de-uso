package progra2.SistemaMedico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class SistemaMedicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaMedicoApplication.class, args);
	}

}
