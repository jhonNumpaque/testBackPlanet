package com.trycore.planets.controllers;

import com.trycore.planets.models.dto.PersonDTO;
import com.trycore.planets.models.entities.Person;
import com.trycore.planets.models.entities.Planet;
import com.trycore.planets.repositories.PersonRepository;
import com.trycore.planets.repositories.PlanetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/persons")
public class PersonController {
    Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired PersonRepository personRepository;
    @Autowired PlanetRepository planetRepository;

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        log.info("Start list person");
        List<Person> people = personRepository.findAll();
        if (people.isEmpty()) {
            log.info("Empty list people");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }  else {
            log.info("End list person");
            return new ResponseEntity<>(people, HttpStatus.OK);
        }
    }

    @GetMapping("/planet/{planet}")
    public ResponseEntity<Map<String, Object>> getAllByPlanet(@PathVariable("planet") int planet) {
        log.info("Start get data by planet");
        int countPersons = personRepository.countPersonByPlanet(planet);
        List<Person> people = personRepository.getAllByPlanet(planet);
        if (people.isEmpty()) {
            log.info("List of people is empty");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }  else {
            log.info("End get data by planet");
           return new ResponseEntity<>(Map.of("list", people, "count", countPersons), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") int id) {
        log.info("Start get person by id, {}", id);
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()) {
            log.info("Empty data");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            log.info("End get person by id, {}", id);
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody PersonDTO person) {
        try {
            log.info("Start save person");
            Optional<Planet> planet = planetRepository.findById(person.getPlanet());
            if (planet.isEmpty()) return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            Person _person = personRepository.save(Person.builder()
                            .age(person.getAge())
                            .birthdate(person.getBirthdate())
                            .document(person.getDocument())
                            .gender(person.getGender())
                            .height(person.getHeight())
                            .planet(planet.get())
                            .name(person.getName())
                            .views(0)
                            .weight(person.getWeight())
                            .build());
            log.info("End save person");
            return new ResponseEntity<>(_person, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable("id") int id) {
        log.info("Start update person, {}", id);
        Optional<Person> personData = personRepository.findById(id);
        if (personData.isPresent()) {
            Person _person = personData.get();
            _person.setViews(_person.getViews() + 1);
            log.info("End update person, {}", id);
            return new ResponseEntity<>(personRepository.save(_person), HttpStatus.OK);
        } else {
            log.info("Empty data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
