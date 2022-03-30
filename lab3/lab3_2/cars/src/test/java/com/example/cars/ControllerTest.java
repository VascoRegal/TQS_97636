package com.example.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import com.example.cars.controllers.CarController;
import com.example.cars.models.Car;
import com.example.cars.services.CarManagerService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class ControllerTest {

    @Autowired
    private MockMvc mvc;   

    @MockBean
    private CarManagerService service;


    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car car = new Car("Toyota", "Corolla");

        when( service.save(Mockito.any())).thenReturn(car);

        mvc.perform(
                post("/api/cars/").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Toyota")));

        verify(service, times(1)).save(Mockito.any());

    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car tcorolla = new Car("Toyota", "Corolla");
        Car fpunto = new Car("Fiat", "Punto");
        Car mpollara  = new Car("Mercedes", "Pollara");

        List<Car> allCars = Arrays.asList(tcorolla, fpunto, mpollara);

        when( service.getAllCars()).thenReturn(allCars);

        mvc.perform(
                get("/api/cars/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(tcorolla.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(fpunto.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(mpollara.getMaker())));
        verify(service, times(1)).getAllCars();
    }
}