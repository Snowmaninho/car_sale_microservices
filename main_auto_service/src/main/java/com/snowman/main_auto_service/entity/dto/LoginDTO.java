package com.snowman.main_auto_service.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class LoginDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    @Size(max = 50)
    private String password;

}
