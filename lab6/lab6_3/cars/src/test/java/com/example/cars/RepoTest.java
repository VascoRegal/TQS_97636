package com.example.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import com.example.cars.models.Car;
import com.example.cars.repositories.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    /*
    @Test
    void whenFindToyotaBId_thenReturnToyota() {
        Car toyota = new Car("Toyota", "Corolla");
        toyota.setCarId(10L);
        entityManager.persistAndFlush(toyota); 

        Car found = carRepository.findByCarId(10L);
        assertThat( found ).isEqualTo(toyota);
    }
    */

    @Test
    void whenInvalidCarId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-34L);
        assertThat(fromDb).isNull();
    }


    @Test
    void givenCarArray_PersistAndFindAll() {

        Car toyota = new Car("Toyota", "Corolla");
        Car opel = new Car("Opel", "Corsa");
        Car mazda = new Car("Mazda", "MX5");


        entityManager.persist(toyota);
        entityManager.persist(opel);
        entityManager.persist(mazda);
        entityManager.flush();

        List<Car> allEmployees = carRepository.findAll();

        assertThat(allEmployees).hasSize(3).extracting(Car::getMaker).containsOnly(toyota.getMaker(), opel.getMaker(), mazda.getMaker());
    }

}