package com.servicio.reservas.usuarios.domain.repository;

import com.servicio.reservas.usuarios.domain.entities.User;

public interface IUserRepository {
    User save(User user);
}
