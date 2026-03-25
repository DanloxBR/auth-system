package com.danieldev.authsystem.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100)
    private String password;
}
