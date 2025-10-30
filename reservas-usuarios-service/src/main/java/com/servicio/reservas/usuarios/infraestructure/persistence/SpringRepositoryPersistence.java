package com.servicio.reservas.usuarios.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringRepositoryPersistence extends JpaRepository<UserModel, Long> {
    List<UserModel> findByActiveTrue();
}
