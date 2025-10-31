package com.servicio.reservas.usuarios.infraestructure.persistence;

import com.servicio.reservas.usuarios.domain.entities.User;

public class UserModelMapper {
    public static UserModel toModel(User user){
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setEmail(user.getEmail());
        userModel.setPassword(user.getPassword());
        userModel.setName(user.getName());
        userModel.setPhoneNumber(user.getPhone_number());
        userModel.setRole(user.getRole());
        userModel.setActive(user.getActive());

        return userModel;
    }

    public static User toDomain (UserModel userModel){
        User user = new User();
        user.setId(userModel.getId());
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setPassword(userModel.getPassword());
        user.setRole(userModel.getRole());
        user.setPhone_number(userModel.getPhoneNumber());

        return user;
    }
}
