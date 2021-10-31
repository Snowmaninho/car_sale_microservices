package com.snowman.main_auto_service.controllers;

import lombok.Data;
// Эта Dto только для логин запроса
@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
