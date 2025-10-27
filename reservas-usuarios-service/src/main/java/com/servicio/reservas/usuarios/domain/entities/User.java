package com.servicio.reservas.usuarios.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String rol;
    private String name;
    private String email;
    private String password;
    private String phone_number;
}
