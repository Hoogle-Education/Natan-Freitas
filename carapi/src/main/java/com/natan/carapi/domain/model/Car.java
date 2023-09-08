package com.natan.carapi.domain.model;

import com.natan.carapi.domain.enums.Brand;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    private Long idChassi;
    private String name;

    @Enumerated(EnumType.STRING)
    private Brand brand;
    private String color;
    private String fabricationYear;

}
