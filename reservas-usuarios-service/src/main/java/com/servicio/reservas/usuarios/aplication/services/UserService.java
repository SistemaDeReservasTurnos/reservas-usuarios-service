package com.servicio.reservas.usuarios.aplication.services;

import com.servicio.reservas.usuarios.aplication.dto.*;
import com.servicio.reservas.usuarios.domain.entities.User;
import com.servicio.reservas.usuarios.domain.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.servicio.reservas.usuarios.infraestructure.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createuser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        User newUser = UserMapper.toDomain(userRequest);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        return UserMapper.toResponse(userRepository.save(newUser));
    }

    @Override
    public List<UserResponse> getAllByRole(String role) {
        List<User> users = userRepository.findAllByRole(role);
        return users.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return UserMapper.toResponse(userRepository.getByEmail(email));
    }

    @Override
    public UserResponse getUserById(Long id) {
        return UserMapper.toResponse(userRepository.getUserById(id));
    }

    @Override
    public void deactivateUser(String email) {
        userRepository.deactivate(email);
    }

    @Override
    public void updateUser(String email, String column, String value) {
        List<String> allowedColumns = List.of("name", "phone_number");
        if (!allowedColumns.contains(column)) {
            throw new BusinessException("Field not allowed for updates: " + column);
        }

        userRepository.update(email, column, value);
    }

    @Override
    public void updatePassword(String email, UpdatePasswordRequest request) {
        User user = userRepository.getByEmail(email);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException("Current passwords do not match");
        }
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BusinessException("New password can't be the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateEmail(String email, UpdateEmailRequest request) {
        User user = userRepository.getByEmail(email);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException("Current passwords do not match");
        }
        if (request.getNewEmail().equals(user.getEmail())) {
            throw new BusinessException("New email can't be the same");
        }
        if (userRepository.existsByEmail(request.getNewEmail())) {
            throw new BusinessException("Email already exists");
        }

        user.setEmail(request.getNewEmail());
        userRepository.save(user);
    }
}
