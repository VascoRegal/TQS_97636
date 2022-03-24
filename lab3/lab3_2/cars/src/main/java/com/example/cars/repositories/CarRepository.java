package com.example.cars.repositories;

import com.example.cars.models.Car;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    
}
