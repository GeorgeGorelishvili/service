package org.george.car.service.service;

import org.george.car.service.domain.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findByUsername(String username);

    List<UserEntity> init();

    List<UserEntity> all();

    List<UserEntity> find(UserEntity user);

    UserEntity saveUser(UserEntity user);

    UserEntity updateVehicleEntity(Integer userId, Integer vehicleId);
}
