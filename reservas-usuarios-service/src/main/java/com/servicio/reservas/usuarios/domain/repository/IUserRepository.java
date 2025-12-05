package com.servicio.reservas.usuarios.domain.repository;

import com.servicio.reservas.usuarios.domain.entities.User;
import com.servicio.reservas.usuarios.infraestructure.persistence.UserModel;

import java.util.List;

public interface IUserRepository {
    User save(User user);
    List<User> findAllByRole(String role);
    void deactivate(String email);
    User getByEmail(String email);
    void update(String email, String column, String value);
    Boolean existsByEmail(String email);
    User getUserById(Long id);
}
