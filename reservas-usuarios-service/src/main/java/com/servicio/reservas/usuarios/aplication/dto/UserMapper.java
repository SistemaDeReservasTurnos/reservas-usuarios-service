package com.servicio.reservas.usuarios.aplication.dto;

import com.servicio.reservas.usuarios.domain.entities.User;

public class UserMapper {
    public static User toDomain(UserRequest userRequest){
        User newUser = new User();
        newUser.setName(userRequest.getName());
        newUser.setPassword(userRequest.getPassword());
        newUser.setPhone_number(userRequest.getPhone_number());
        newUser.setEmail(userRequest.getEmail());
        newUser.setRol(userRequest.getRol());

        return newUser;
    }

    public static UserResponse toResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone_number(user.getPhone_number());
        userResponse.setRol(user.getRol());

        return userResponse;
    }

}
