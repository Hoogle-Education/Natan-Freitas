package com.natan.carapi.controller;

import com.natan.carapi.domain.dto.CarRequestDto;
import com.natan.carapi.domain.dto.CarResponseDto;
import com.natan.carapi.domain.model.Car;
import com.natan.carapi.infra.exceptions.CarNotFoundedException;
import com.natan.carapi.infra.service.CarService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    @NonNull
    private CarService carService;

    @GetMapping("/get/{idChassi}")
    public ResponseEntity<CarResponseDto> getCarByIdChassi(
        @PathVariable Long idChassi
    ) throws CarNotFoundedException {
        var carFounded = carService.getByIdChassi(idChassi);
        return ResponseEntity.ok(carFounded);
    }

    @PostMapping("/post")
    @Transactional
    public ResponseEntity<?> registerNewCar(
            @Valid @RequestBody CarRequestDto carRequest,
            UriComponentsBuilder uriBuilder
    ) {
        var carRegistered = carService.registerNewCar(carRequest);

        if(carRegistered == null) {
            return ResponseEntity.badRequest().body("Car already registered");
        }

        var uri = uriBuilder
                .path("/cars/get/{idChassi}")
                .buildAndExpand(carRegistered.getIdChassi())
                .toUri();

        return ResponseEntity.created(uri).body(carRegistered);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(CarNotFoundedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleCarNotFoundedException(
            CarNotFoundedException exception
    ) {
        return ResponseEntity.notFound().build();
    }
}
