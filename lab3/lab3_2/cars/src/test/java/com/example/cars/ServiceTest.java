package com.example.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.cars.models.Car;
import com.example.cars.repositories.CarRepository;
import com.example.cars.services.CarManagerService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {

        Car toyota = new Car("Toyota", "Corolla");
        toyota.setCarId(152L);

        Car opel = new Car("Opel", "Corsa");
        Car mazda = new Car("Mazda", "MX5");

        List<Car> cars = Arrays.asList(toyota, opel, mazda);

        Mockito.when(carRepository.findByCarId(toyota.getCarId())).thenReturn(toyota);
        Mockito.when(carRepository.findById(opel.getCarId())).thenReturn(Optional.of(opel));
        Mockito.when(carRepository.findAll()).thenReturn(cars);
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
     void whenSearchValidId_thenCarShouldBeFound() {
        Car found = carManagerService.getCarDetails(152L);

        assertThat(found.getMaker()).isEqualTo("Toyota");
    }

    @Test
     void whenSearchInvalidId_thenEmployeeShouldNotBeFound() {
        Car fromDb = carManagerService.getCarDetails(-99L);
        assertThat(fromDb).isNull();
    }


    @Test
     void given3Employees_whengetAll_thenReturn3Records() {
        Car toyota = new Car("Toyota", "Corolla");
        Car opel = new Car("Opel", "Corsa");
        Car mazda = new Car("Mazda", "MX5");

        List<Car> cars = carManagerService.getAllCars();
        verifyGetAllCarsIsCalledOnce();
        assertThat(cars).hasSize(3).extracting(Car::getMaker).contains(toyota.getMaker(), opel.getMaker(), mazda.getMaker());
    }


    private void verifyGetAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}

