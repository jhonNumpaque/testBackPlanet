package com.trycore.planets.repositories;

import com.trycore.planets.models.entities.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Integer> {
}
