package com.trycore.planets.controllers;

import com.trycore.planets.models.dto.PlanetDTO;
import com.trycore.planets.models.entities.Planet;
import com.trycore.planets.repositories.PlanetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/planets")
public class PlanetController {
    Logger log = LoggerFactory.getLogger(PlanetController.class);
    @Autowired PlanetRepository planetRepository;

    @GetMapping
    public ResponseEntity<List<Planet>> getAll() {
        log.info("Start list of planets");
        List<Planet> planets = planetRepository.findAll();
        if (planets.isEmpty()) {
            log.info("Empty list");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("End list of planets");
            return new ResponseEntity<>(planets, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> getById(@PathVariable("id") int id) {
        log.info("Start get planet by id, {}", id);
        Optional<Planet> planetData = planetRepository.findById(id);
        if(planetData.isEmpty()) {
            log.info("Empty get planet");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("End get planet by id, {}", id);
            return new ResponseEntity<>(planetData.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Planet> save(@RequestBody PlanetDTO planet) {
        try {
            log.info("Start save planet");
            Planet _planet = planetRepository.save(Planet.builder()
                            .diameter(planet.getDiameter())
                            .ground(planet.getGround())
                            .name(planet.getName())
                            .rotationPeriod(planet.getRotationPeriod())
                            .views(0)
                            .weather(planet.getWeather())
                            .build());
            log.info("End save planet");
            return new ResponseEntity<>(_planet, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planet> update(@PathVariable("id") int id) {
        log.info("Start update planet, {}", id);
        Optional<Planet> planetData = planetRepository.findById(id);
        if (planetData.isPresent()) {
            Planet _planet = planetData.get();
            _planet.setViews(_planet.getViews() + 1);
            log.info("End update planet, {}", id);
            return new ResponseEntity<>(planetRepository.save(_planet), HttpStatus.OK);
        } else {
            log.info("Empty data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
