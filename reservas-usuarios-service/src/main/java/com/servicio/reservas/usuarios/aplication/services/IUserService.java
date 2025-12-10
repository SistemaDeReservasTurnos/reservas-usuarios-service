package com.servicio.reservas.usuarios.aplication.services;

import com.servicio.reservas.usuarios.aplication.dto.*;

import java.util.List;

public interface IUserService {
    UserResponse createuser(UserRequest userRequest);
    List<UserResponse> getAllByRole(String role);
    void deactivateUser(String email);
    UserResponse getUserByEmail(String email);
    void updateUser(UpdateUserRequest request);
    void updatePassword(String email, UpdatePasswordRequest request);
    void updateEmail(String email, UpdateEmailRequest request);
    UserResponse getUserById(Long id);
}
