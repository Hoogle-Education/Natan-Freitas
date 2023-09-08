package com.natan.carapi.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CarRequestDto {
    @NotNull
    private Long idChassi;

    @NotNull
    private String name;

    @NotNull
    private String brand;

    @NotNull
    private String color;

    @NotNull
    private String fabricationYear;
}

