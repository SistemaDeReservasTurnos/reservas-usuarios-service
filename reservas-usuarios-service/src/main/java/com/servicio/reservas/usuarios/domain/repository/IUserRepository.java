package com.servicio.reservas.usuarios.domain.repository;

import com.servicio.reservas.usuarios.aplication.dto.updateCredentialRequest;
import com.servicio.reservas.usuarios.domain.entities.User;

import java.util.List;

public interface IUserRepository {
    User save(User user);
    List<User> findAllByRole(String role);
    void deactivate(String email);
    User getByEmail(String email);
    void update(String email, String column, String value);
    void updateCredential(String email, updateCredentialRequest request);
}
