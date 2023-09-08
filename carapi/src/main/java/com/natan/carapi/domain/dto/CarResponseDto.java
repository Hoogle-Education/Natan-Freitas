package com.natan.carapi.domain.dto;

import com.natan.carapi.domain.model.Car;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponseDto {
    private Long idChassi;
    private String name;
    private String brand;
    private String color;
    private String fabricationYear;

    private String captilize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public CarResponseDto(Car car) {
        this.idChassi = car.getIdChassi();
        this.name = car.getName();
        this.brand = captilize(car.getBrand().toString());
        this.color = car.getColor();
        this.fabricationYear = car.getFabricationYear();
    }
}
