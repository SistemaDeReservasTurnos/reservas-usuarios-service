package com.servicio.reservas.usuarios.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SpringRepositoryPersistence extends JpaRepository<UserModel, Long> {
    List<UserModel> findByActiveTrueAndRol(String role);
    Optional<UserModel> findByEmailAndActiveTrue(String email);
}
