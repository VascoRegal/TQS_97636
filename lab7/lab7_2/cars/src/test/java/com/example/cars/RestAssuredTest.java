package com.example.cars;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.example.cars.controllers.CarController;
import com.example.cars.models.Car;
import com.example.cars.services.CarManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CarController.class)
public class RestAssuredTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CarManagerService service;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenPostCar_thenCreateCar() throws JsonProcessingException {
        Car car = new Car("Toyota", "Corolla");

        when( service.save(Mockito.any())).thenReturn(car);

        RestAssuredMockMvc
            .given().contentType(ContentType.JSON).body(toJson(car))
            .when().post("/api/cars/")
            .then().contentType(ContentType.JSON).status(HttpStatus.CREATED)
            .body("model", equalTo(car.getModel()));

        verify(this.service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car tcorolla = new Car("Toyota", "Corolla");
        Car fpunto = new Car("Fiat", "Punto");
        Car mpollara  = new Car("Mercedes", "Pollara");

        List<Car> allCars = Arrays.asList(tcorolla, fpunto, mpollara);

        when( service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc
            .when()
            .get("/api/cars/")
            .then().contentType(ContentType.JSON).status(HttpStatus.OK)
            .body("model", hasItems(tcorolla.getModel(), fpunto.getModel(), mpollara.getModel()));

        verify(service, times(1)).getAllCars();
    }


    byte[] toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
