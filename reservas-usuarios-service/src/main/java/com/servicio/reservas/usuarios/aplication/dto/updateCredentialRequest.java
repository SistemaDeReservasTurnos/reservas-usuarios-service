package com.servicio.reservas.usuarios.aplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateCredentialRequest {
    private String currentPassword;
    private String newPassword;
    private String newEmail;
}
