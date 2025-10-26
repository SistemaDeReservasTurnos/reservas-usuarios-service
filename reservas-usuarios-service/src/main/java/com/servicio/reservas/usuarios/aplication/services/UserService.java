package com.servicio.reservas.usuarios.aplication.services;

import com.servicio.reservas.usuarios.aplication.dto.UserMapper;
import com.servicio.reservas.usuarios.aplication.dto.UserRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserResponse;
import com.servicio.reservas.usuarios.domain.entities.User;
import com.servicio.reservas.usuarios.domain.repository.IUserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService implements  IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserResponse createuser(UserRequest userRequest) {
        User newUser = UserMapper.toDomain(userRequest);
        return UserMapper.toResponse(userRepository.save(newUser));
    }
}
