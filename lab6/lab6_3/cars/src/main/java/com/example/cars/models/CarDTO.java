package com.example.cars.models;


public class CarDTO {
    private Long carId;

    private String maker;
    private String model;

    public static CarDTO fromCarEntity(Car car) {
        return new CarDTO(car.getMaker(), car.getModel(), car.getCarId());
    }

    public Car toCarEntity() {
        return new Car(this.getMaker(), this.getModel(), this.getCarId());
    }

    public CarDTO(String maker, String model, Long id) {
        this.maker = maker;
        this.model = model;
        this.carId = id;
    }


    public CarDTO() {
    }

    public Long getCarId() {
        return this.carId;
    }

    public void setCarId(Long id) {
        this.carId = id;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
