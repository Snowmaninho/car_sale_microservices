package com.snowman.main_auto_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
@AllArgsConstructor
public class FilterFormDTO {

    private String carMake;
    private String carModel;
    private Integer carMinYear;
    private Integer carMaxYear;
    private Integer carMinPrice;
    private Integer carMaxPrice;
    private Integer carMinHp;
    private Integer carMaxHp;

}