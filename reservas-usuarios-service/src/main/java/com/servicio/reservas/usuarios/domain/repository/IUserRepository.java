package com.servicio.reservas.usuarios.domain.repository;

import com.servicio.reservas.usuarios.domain.entities.User;

import java.util.List;

public interface IUserRepository {
    User save(User user);
    List<User> findAllByRole(String role);
    void deactivate(Long id);
    User getByEmail(String email);
}
