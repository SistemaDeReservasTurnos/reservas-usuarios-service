package com.servicio.reservas.usuarios.infraestructure.persistence;

import com.servicio.reservas.usuarios.aplication.dto.updateCredentialRequest;
import com.servicio.reservas.usuarios.domain.entities.User;
import com.servicio.reservas.usuarios.domain.repository.IUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepositoryPersistence implements IUserRepository {

    private final SpringRepositoryPersistence springUserRepositoryPersistence;

    public UserRepositoryPersistence(SpringRepositoryPersistence springUserRepositoryPersistence) {
        this.springUserRepositoryPersistence = springUserRepositoryPersistence;
    }

    @Override
    public User save(User user){
        UserModel userModel = UserModelMapper.toModel(user);
        return UserModelMapper.toDomain(springUserRepositoryPersistence.save(userModel));
    }

    @Override
    public List<User> findAllByRole(String role){
        List<UserModel> models = springUserRepositoryPersistence.findByActiveTrueAndRole(role);
        return models.stream()
                .map(UserModelMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email){
        UserModel userModel = springUserRepositoryPersistence.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UserModelMapper.toDomain(userModel);
    }

    @Override
    public void deactivate(String email){
        UserModel userModel = springUserRepositoryPersistence.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        userModel.setActive(false);
        springUserRepositoryPersistence.save(userModel);
    }

    @Override
    public void update(String email, String column, String value){
        UserModel user = springUserRepositoryPersistence.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        switch (column) {
            case "name" -> user.setName(value);
            case "phone_number" -> user.setPhoneNumber(value);
        }

        springUserRepositoryPersistence.save(user);
    }

    @Override
    public void updateCredential(String email, updateCredentialRequest request){
        UserModel user = springUserRepositoryPersistence.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(!request.getCurrentPassword().equals(user.getPassword())){
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        if(request.getNewPassword() != null && !request.getNewPassword().isBlank()){
            if(request.getNewPassword().equals(user.getPassword())){
                throw new RuntimeException("La nueva contraseña no puede ser igual a la anterior");
            }
            user.setPassword(request.getNewPassword());
        }

        if(request.getNewEmail() != null && !request.getNewEmail().isBlank()){
            if(springUserRepositoryPersistence.existsByEmail(request.getNewEmail())){
                throw new RuntimeException("El nuevo correo ya está en uso");
            }
            user.setEmail(request.getNewEmail());
        }

        springUserRepositoryPersistence.save(user);
    }
}
