package com.snowman.common_libs.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Data
@NoArgsConstructor
@Entity
@Table(name = "offers")
@Accessors(chain = true)
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String anonsName;
    private String authorName;

    @OneToOne(targetEntity=Car.class, fetch= FetchType.EAGER)
    private Car car;

    private String offerText;

}
