package com.snowman.main_auto_service.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@ToString
public class OfferDTO {

    @NotEmpty
    @Size(max = 50)
    private String title;

    @NotEmpty
    @Size(max = 50)
    private String anonsName;

    @NotEmpty
    @Size(max = 25)
    private String carMake;

    @NotEmpty
    @Size(max = 25)
    private String carModel;

    @Max(value = 2021, message = "Year should not be greater than 2021")
    @NotNull
    @Positive
    private Integer carYear;

    @NotNull
    @Positive
    private Integer carPower;

    @NotNull
    @Positive
    private Integer carPrice;

    @NotEmpty
    @Size(max = 300)
    private String offerText;
}
