package com.servicio.reservas.usuarios.infraestructure.persistence;

import com.servicio.reservas.usuarios.domain.entities.User;
import com.servicio.reservas.usuarios.domain.repository.IUserRepository;
import com.servicio.reservas.usuarios.infraestructure.exceptions.ResourceNotFoundException;
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
        UserModel userModel = getActiveUserModelByEmail(email);
        return UserModelMapper.toDomain(userModel);
    }

    @Override
    public User getUserById(Long id){
        UserModel userModel = getActiveUserModelById(id);
        return UserModelMapper.toDomain(userModel);
    }

    @Override
    public void deactivate(String email){
        UserModel userModel = getActiveUserModelByEmail(email);

        userModel.setActive(false);
        springUserRepositoryPersistence.save(userModel);
    }

    @Override
    public void update(String email, String column, String value){
        UserModel user = getActiveUserModelByEmail(email);

        switch (column) {
            case "name" -> user.setName(value);
            case "phone_number" -> user.setPhoneNumber(value);
        }

        springUserRepositoryPersistence.save(user);
    }

    @Override
    public Boolean existsByEmail(String email){
        return springUserRepositoryPersistence.existsByEmail(email);
    }

    private UserModel getActiveUserModelByEmail(String email) {
        return springUserRepositoryPersistence.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the email: " + email));
    }

    private UserModel getActiveUserModelById(Long id){
        return springUserRepositoryPersistence.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with the id: " + id));
    }
}
