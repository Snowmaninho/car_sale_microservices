package com.snowman.main_auto_service.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
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