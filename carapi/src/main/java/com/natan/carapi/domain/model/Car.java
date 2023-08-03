package com.natan.carapi.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    private Long idChassi;


}
