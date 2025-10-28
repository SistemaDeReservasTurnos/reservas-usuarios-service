package com.servicio.reservas.usuarios.infraestructure.persistence;

import com.servicio.reservas.usuarios.domain.entities.User;
import com.servicio.reservas.usuarios.domain.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepositoryPersistence implements IUserRepository {
    private final SpringRepositoryPersistence userRepositoryPersistence;

    public UserRepositoryPersistence(SpringRepositoryPersistence userRepositoryPersistence) {
        this.userRepositoryPersistence = userRepositoryPersistence;
    }

    @Override
    public User save(User user){
        UserModel userModel = UserModelMapper.toModel(user);
        return UserModelMapper.toDomain(userRepositoryPersistence.save(userModel));
    }

    @Override
    public List<User> findAll(){
        List<UserModel> models = userRepositoryPersistence.findAll();
        return models.stream()
                .map(UserModelMapper::toDomain)
                .collect(Collectors.toList());
    }
}
