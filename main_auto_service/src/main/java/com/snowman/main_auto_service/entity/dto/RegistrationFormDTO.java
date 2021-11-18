package com.snowman.main_auto_service.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@ToString
public class RegistrationFormDTO {

    @NotEmpty
    @Size(min=2, max=30)
    private String username;

    @NotEmpty
    @Size(max = 50)
    private String firstName;

    @NotEmpty
    @Size(max = 50)
    private String lastName;

    @NotEmpty
    @Size(max = 50)
    @Email
    private String email;

    @NotEmpty
    @Size(max = 50)
    private String password;

    @NotEmpty
    @Size(max = 50)
    private String confirmPassword;
}
