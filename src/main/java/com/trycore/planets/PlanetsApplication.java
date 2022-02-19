package com.trycore.planets;

import com.trycore.planets.models.entities.Planet;
import com.trycore.planets.repositories.PlanetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlanetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanetsApplication.class, args);
	}

	@Bean
	CommandLineRunner start(PlanetRepository planetRepository){
		return args -> {
			planetRepository.save(Planet
							.builder()
							.views(1)
							.weather("Frio")
							.name("Neptuno")
							.rotationPeriod("0,671 días (16h 6min 14s)")
							.ground("Acuatico")
							.diameter("49.572 km")
							.build());

			planetRepository.save(Planet
					.builder()
					.views(2)
					.weather("Ardiente")
					.name("Mercurio")
					.rotationPeriod("58,7 días")
					.ground("Gas")
					.diameter("4879,4 km")
					.build());

			planetRepository.save(Planet
					.builder()
					.views(3)
					.weather("Desertico")
					.name("Marte")
					.rotationPeriod("24,6229 horas")
					.ground("Desetrico")
					.diameter("6794,4 km")
					.build());

			planetRepository.save(Planet
					.builder()
					.views(4)
					.weather("Calido")
					.name("Venus")
					.rotationPeriod("243,0187 días")
					.ground("Gaseoso")
					.diameter("12 103,6 km")
					.build());
		};
	}
}
