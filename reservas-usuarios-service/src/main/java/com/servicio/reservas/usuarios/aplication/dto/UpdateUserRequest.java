package com.servicio.reservas.usuarios.aplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "id is required")
    private Long id;

    private String name;
    private String email;
    private String phone_number;
}
