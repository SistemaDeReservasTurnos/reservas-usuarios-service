package com.servicio.reservas.usuarios.aplication.services;

import com.servicio.reservas.usuarios.aplication.dto.UserMapper;
import com.servicio.reservas.usuarios.aplication.dto.UserRequest;
import com.servicio.reservas.usuarios.aplication.dto.UserResponse;
import com.servicio.reservas.usuarios.domain.entities.User;
import com.servicio.reservas.usuarios.domain.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public List<UserResponse> getAllByRole(String role){
        List<User> users =  userRepository.findAllByRole(role);
        return users.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return UserMapper.toResponse( userRepository.getByEmail(email));
    }

    @Override
    public void deactivateUser(String email) {
        userRepository.deactivate(email);
    }

}
