package com.trycore.planets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class PlanetControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    private final String URL_API = "/api/planets";

    @Test
    public void TestingListPlanetsWithEmptyContent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL_API).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void TestingGetPlanetWithEmptyContent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL_API + "/9999").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void TestingSavingAndListPlanetsWithResultData() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Planet.builder()
                                        .views(0)
                                        .weather("Frio")
                                        .name("Kepler-186f")
                                        .rotationPeriod("Indeterminado")
                                        .ground("Terrario")
                                        .diameter("70 081 km")
                                .build())))
                .andExpect(status().isCreated())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.get(URL_API).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void TestingSavingAndGettingOneRecord() throws Exception {
        MvcResult resultData = mvc.perform(
                        MockMvcRequestBuilders.post(URL_API)
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


        Planet response = objectMapper.readValue(resultData.getResponse().getContentAsString(), Planet.class);

        mvc.perform(MockMvcRequestBuilders.get(URL_API + "/" + response.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void TestingFailSavingPlanet() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.post(URL_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Planet.builder()
                                        .views(0)
                                        .weather(null)
                                        .name("Kepler-186f")
                                        .rotationPeriod("0.5")
                                        .ground("Terrario")
                                        .diameter("70 081 km")
                                        .build())))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void TestingSavingDataAndUpdate() throws Exception {
        MvcResult resultData = mvc.perform(
                        MockMvcRequestBuilders.post(URL_API)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Planet.builder()
                                        .views(0)
                                        .weather("Desertico")
                                        .name("Pluton")
                                        .rotationPeriod("6,39 días terrestres (153 horas con 21 minutos)")
                                        .ground("Terrestre")
                                        .diameter("2370 km")
                                        .build())))
                .andExpect(status().isCreated())
                .andReturn();

        Planet response = objectMapper.readValue(resultData.getResponse().getContentAsString(), Planet.class);

        mvc.perform(MockMvcRequestBuilders.put(URL_API + "/" + response.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void TestingUpdatePlanetFail() throws  Exception {
        mvc.perform(MockMvcRequestBuilders.put(URL_API + "/9999").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}