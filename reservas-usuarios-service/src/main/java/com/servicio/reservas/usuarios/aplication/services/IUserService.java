package com.servicio.reservas.usuarios.aplication.services;

import com.servicio.reservas.usuarios.aplication.dto.UserRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserResponse;
import com.servicio.reservas.usuarios.aplication.dto.updateCredentialRequest;

import java.util.List;

public interface IUserService {
    UserResponse createuser(UserRequest userRequest);
    List<UserResponse> getAllByRole(String role);
    void deactivateUser(String email);
    UserResponse getUserByEmail(String email);
    void updateUser(String email, String column, String value);
    void updateCredential(String email, updateCredentialRequest request);
}
