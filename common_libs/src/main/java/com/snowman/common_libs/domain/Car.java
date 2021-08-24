package com.snowman.common_libs.domain;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "cars")
@Accessors(chain = true)
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String carMake;
    private String carModel;
    private int carYear;
    private int carPrice;
    private int carPower;

    public Car() {
    }
}
