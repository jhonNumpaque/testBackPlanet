package com.trycore.planets.repositories;

import com.trycore.planets.models.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT COUNT(p) FROM Person  p WHERE p.planet.id = ?1")
    Integer countPersonByPlanet(int planetId);

    @Query("SELECT p FROM Person p WHERE p.planet.id = ?1")
    List<Person> getAllByPlanet(int planet);
}
