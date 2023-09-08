package com.natan.carapi.infra.service;

import com.natan.carapi.domain.dto.CarRequestDto;
import com.natan.carapi.domain.dto.CarResponseDto;
import com.natan.carapi.domain.enums.Brand;
import com.natan.carapi.domain.model.Car;
import com.natan.carapi.infra.exceptions.CarNotFoundedException;
import com.natan.carapi.infra.exceptions.InvalidBrandException;
import com.natan.carapi.infra.repository.CarRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CarService {

    @NonNull
    private CarRepository carRepository;

    public CarResponseDto getByIdChassi(Long idChassi)
            throws CarNotFoundedException {
        var searchByIdChassi = carRepository.findById(idChassi);

        if(searchByIdChassi.isEmpty()) {
            throw new CarNotFoundedException("Car not found");
        }

        var car = searchByIdChassi.get();
        return new CarResponseDto(car);
    }

    public CarResponseDto registerNewCar(CarRequestDto carRequest)
            throws InvalidBrandException {

        if(hasInvalidBrand(carRequest.getBrand())) {
            throw new InvalidBrandException("Invalid brand");
        }

        var carToSave = toCar(carRequest);
        // TODO: not save duplicated cars
        var carSaved = carRepository.save(carToSave);
        return new CarResponseDto(carSaved);
    }

    private boolean hasInvalidBrand(String brand) {
        return Arrays.stream(Brand.values())
                .noneMatch(brandEnum -> brandEnum.name().equals(brand.toUpperCase()));
    }

    private Car toCar(CarRequestDto carRequest) {
        var car = new Car();
        car.setIdChassi(carRequest.getIdChassi());
        car.setName(carRequest.getName());
        car.setBrand(Brand.valueOf(carRequest.getBrand().toUpperCase()));
        car.setColor(carRequest.getColor());
        car.setFabricationYear(carRequest.getFabricationYear());
        return car;
    }

}
