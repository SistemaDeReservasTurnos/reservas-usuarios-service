package com.servicio.reservas.usuarios.aplication.services;

import com.servicio.reservas.usuarios.aplication.dto.*;
import com.servicio.reservas.usuarios.domain.entities.User;
import com.servicio.reservas.usuarios.domain.repository.IUserRepository;
import com.servicio.reservas.usuarios.infraestructure.exceptions.CustomExcepction;
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
    public UserResponse getUserById(Long id){
        return UserMapper.toResponse(userRepository.getUserById(id));
    }

    @Override
    public void deactivateUser(String email) {
        userRepository.deactivate(email);
    }

    @Override
    public void updateUser(String email, String column, String value){
        List<String> allowedColumns = List.of("name", "phone_number");
        if (!allowedColumns.contains(column)) {
            throw new CustomExcepction("Field not allowed for updates: " + column);
        }

        userRepository.update(email, column, value);
    }

    @Override
    public void updatePassword(String email, UpdatePasswordRequest request){
        User user = userRepository.getByEmail(email);

        if (!request.getCurrentPassword().equals(user.getPassword())) {
            throw new CustomExcepction("Current passwords do not match");
        }
        if (request.getNewPassword().equals(user.getPassword())) {
            throw new CustomExcepction("New password can't be the same");
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }

    @Override
    public void updateEmail(String email, UpdateEmailRequest request){
        User user = userRepository.getByEmail(email);

        if (!request.getCurrentPassword().equals(user.getPassword())) {
            throw new CustomExcepction("Current passwords do not match");
        }
        if(request.getNewEmail().equals(user.getEmail())) {
            throw new CustomExcepction("New email can't be the same");
        }
        if(userRepository.existsByEmail(request.getNewEmail())){
            throw new CustomExcepction("Email already exists");
        }

        user.setEmail(request.getNewEmail());
        userRepository.save(user);
    }

}
