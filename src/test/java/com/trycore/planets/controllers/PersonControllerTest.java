package com.trycore.planets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trycore.planets.models.dto.PersonDTO;
import com.trycore.planets.models.entities.Person;
import com.trycore.planets.models.entities.Planet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    private final String URL_API = "/api/persons";
    private final String URL_API_PLANET = "/api/planets";

    @Test
    public void TestingListPersonWithEmptyList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL_API).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void TestingSavingDataPlanetAndPersonAndListData() throws Exception {
        MvcResult resultDataPlanet = mvc.perform(
                        MockMvcRequestBuilders.post(URL_API_PLANET)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Planet.builder()
                                        .views(0)
                                        .weather("Frio")
                                        .name("Neptuno")
                                        .rotationPeriod("0,671 días (16h 6min 14s)")
                                        .ground("Acuatico")
                                        .diameter("49.572 km")
                                        .build())))
                .andExpect(status().isCreated())
                .andReturn();


        Planet response = objectMapper.readValue(resultDataPlanet.getResponse().getContentAsString(), Planet.class);

        mvc.perform(
                        MockMvcRequestBuilders.post(URL_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(PersonDTO
                                                .builder()
                                                .planet(response.getId())
                                                .weight("80 kg")
                                                .height("150 cm")
                                                .gender("Masculino")
                                                .age(30)
                                                .document("105420355")
                                                .birthdate("11/02/1998")
                                                .views(0)
                                                .name("Pablo Goméz")
                                                .build())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.get(URL_API).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void TestingSavingDataPlanetAndPersonAndListDataWithPlanet() throws Exception {
        MvcResult resultDataPlanet = mvc.perform(
                        MockMvcRequestBuilders.post(URL_API_PLANET)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Planet.builder()
                                        .views(0)
                                        .weather("Frio")
                                        .name("Neptuno")
                                        .rotationPeriod("0,671 días (16h 6min 14s)")
                                        .ground("Acuatico")
                                        .diameter("49.572 km")
                                        .build())))
                .andExpect(status().isCreated())
                .andReturn();


        Planet response = objectMapper.readValue(resultDataPlanet.getResponse().getContentAsString(), Planet.class);

        mvc.perform(
                        MockMvcRequestBuilders.post(URL_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(PersonDTO
                                        .builder()
                                        .planet(response.getId())
                                        .weight("80 kg")
                                        .height("150 cm")
                                        .gender("Masculino")
                                        .age(30)
                                        .document("105420355")
                                        .birthdate("11/02/1998")
                                        .views(0)
                                        .name("Pablo Goméz")
                                        .build())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.get(URL_API  + "/planet/" + response.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test void TestingFailSavingData() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.post(URL_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(PersonDTO
                                        .builder()
                                        .planet(0)
                                        .weight("80 kg")
                                        .height("150 cm")
                                        .gender("Masculino")
                                        .age(30)
                                        .document("105420355")
                                        .birthdate("11/02/1998")
                                        .views(0)
                                        .name("Pablo Goméz")
                                        .build())))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void TestingListUsersEmptyPlanets() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL_API  + "/planet/9999").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void TestingGetDataPersonById() throws Exception {
        MvcResult resultDataPlanet = mvc.perform(
                        MockMvcRequestBuilders.post(URL_API_PLANET)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Planet.builder()
                                        .views(0)
                                        .weather("Frio")
                                        .name("Neptuno")
                                        .rotationPeriod("0,671 días (16h 6min 14s)")
                                        .ground("Acuatico")
                                        .diameter("49.572 km")
                                        .build())))
                .andExpect(status().isCreated())
                .andReturn();
        Planet response = objectMapper.readValue(resultDataPlanet.getResponse().getContentAsString(), Planet.class);

        MvcResult resultDataPerson = mvc.perform(
                        MockMvcRequestBuilders.post(URL_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(PersonDTO
                                        .builder()
                                        .planet(response.getId())
                                        .weight("80 kg")
                                        .height("150 cm")
                                        .gender("Masculino")
                                        .age(30)
                                        .document("105420355")
                                        .birthdate("11/02/1998")
                                        .views(0)
                                        .name("Pablo Goméz")
                                        .build())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Person person = objectMapper.readValue(resultDataPerson.getResponse().getContentAsString(), Person.class);

        mvc.perform(MockMvcRequestBuilders.get(URL_API  + "/" + person.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(person.getId())));
    }

    @Test
    public void TestingGetPersonByIdEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL_API  + "/999").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void TestingSavingDataAndUpdateView() throws Exception {
        MvcResult resultDataPlanet = mvc.perform(
                        MockMvcRequestBuilders.post(URL_API_PLANET)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Planet.builder()
                                        .views(0)
                                        .weather("Frio")
                                        .name("Neptuno")
                                        .rotationPeriod("0,671 días (16h 6min 14s)")
                                        .ground("Acuatico")
                                        .diameter("49.572 km")
                                        .build())))
                .andExpect(status().isCreated())
                .andReturn();
        Planet response = objectMapper.readValue(resultDataPlanet.getResponse().getContentAsString(), Planet.class);

        MvcResult resultDataPerson = mvc.perform(
                        MockMvcRequestBuilders.post(URL_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(PersonDTO
                                        .builder()
                                        .planet(response.getId())
                                        .weight("80 kg")
                                        .height("150 cm")
                                        .gender("Masculino")
                                        .age(30)
                                        .document("105420355")
                                        .birthdate("11/02/1998")
                                        .views(0)
                                        .name("Pablo Goméz")
                                        .build())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Person person = objectMapper.readValue(resultDataPerson.getResponse().getContentAsString(), Person.class);

        mvc.perform(MockMvcRequestBuilders.put(URL_API  + "/" + person.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(person.getId())))
                .andExpect(jsonPath("$.views", is(1)));
    }

    @Test
    public void TestingUpdateEmptyData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(URL_API  + "/99").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}