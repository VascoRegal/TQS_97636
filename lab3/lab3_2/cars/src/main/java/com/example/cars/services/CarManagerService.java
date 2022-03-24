package com.example.cars.services;

import java.util.List;

import com.example.cars.models.Car;
import com.example.cars.repositories.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarManagerService {
    @Autowired
    private CarRepository carRepository;

    public Car save(Car car) {
        return null;
    }

    public List<Car> getAllCars() {
        return null;
    }

    public Car getCarDetails(Long id) {
        return null;
    }
}
