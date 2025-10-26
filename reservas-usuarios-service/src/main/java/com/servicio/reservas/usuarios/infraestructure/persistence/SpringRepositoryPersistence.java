package com.servicio.reservas.usuarios.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringRepositoryPersistence extends JpaRepository<UserModel, Long> {

}
