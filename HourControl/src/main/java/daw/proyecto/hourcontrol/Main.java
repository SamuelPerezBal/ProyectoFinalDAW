package daw.proyecto.hourcontrol;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import daw.proyecto.hourcontrol.domain.*;
import daw.proyecto.hourcontrol.services.*;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner initData(EmpleadoService usuarioService, FichaService fichaService) {
		return args -> {
			Empleado user1 = new Empleado("Admin", "1234", Rol.ADMIN);
			Empleado user2 = new Empleado("Fernando.Alonso", "1234", Rol.EMPLEADO);
			Empleado user3 = new Empleado("Lewis.Hamilton", "1234", Rol.EMPLEADO);
			usuarioService.añadir(user1);
			usuarioService.añadir(user2);
			usuarioService.añadir(user3);
		};
	}
}
