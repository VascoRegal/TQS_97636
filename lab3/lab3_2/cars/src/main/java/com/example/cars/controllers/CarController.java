package com.example.cars.controllers;

import java.util.List;

import com.example.cars.models.Car;
import com.example.cars.models.CarDTO;
import com.example.cars.services.CarManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarManagerService carManagerService;

    @PostMapping("/")
    public ResponseEntity<Car> createCar(@RequestBody CarDTO car) {
        Car newCar = this.carManagerService.save(car.toCarEntity());
        return new ResponseEntity<Car>(newCar, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<Car> getAllCars() {
        return this.carManagerService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(Long id) {
        Car found = this.carManagerService.getCarDetails(id);
        HttpStatus code;

        if (found == null) {
            code = HttpStatus.NOT_FOUND;
        } else {
            code = HttpStatus.OK;
        }

        return new ResponseEntity<Car>(found, code);
    }
}
