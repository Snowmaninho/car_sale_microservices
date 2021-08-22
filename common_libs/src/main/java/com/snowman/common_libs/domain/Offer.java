package com.snowman.common_libs.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@Entity
@Table(name = "offers")
@Accessors(chain = true)
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String anonsName;
    private String authorName;

    @OneToOne(targetEntity=Car.class, fetch= FetchType.EAGER)
    private Car car;

    private String offerText;


    public Offer() {
    }
}
