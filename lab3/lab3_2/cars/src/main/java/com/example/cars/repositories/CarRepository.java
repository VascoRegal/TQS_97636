package com.example.cars.repositories;

import java.util.List;

import com.example.cars.models.Car;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    public Car findByCarId(Long id);
    public List<Car> findAll();
}
