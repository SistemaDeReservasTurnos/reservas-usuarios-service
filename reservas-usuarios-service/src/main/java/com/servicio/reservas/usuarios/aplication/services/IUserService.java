package com.servicio.reservas.usuarios.aplication.services;

import com.servicio.reservas.usuarios.aplication.dto.UserRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse createuser(UserRequest userRequest);
    List<UserResponse> getAllByRole(String role);
    void deactivateUser(Long id);
    UserResponse getUserByEmail(String email);
}
