package com.servicio.reservas.usuarios.aplication.services;

import com.servicio.reservas.usuarios.aplication.dto.UpdateEmailRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserResponse;
import com.servicio.reservas.usuarios.aplication.dto.UpdatePasswordRequest;
import com.servicio.reservas.usuarios.infraestructure.persistence.UserModel;

import java.util.List;

public interface IUserService {
    UserResponse createuser(UserRequest userRequest);
    List<UserResponse> getAllByRole(String role);
    void deactivateUser(String email);
    UserResponse getUserByEmail(String email);
    void updateUser(String email, String column, String value);
    void updatePassword(String email, UpdatePasswordRequest request);
    void updateEmail(String email, UpdateEmailRequest request);
    UserResponse getUserById(Long id);
}
