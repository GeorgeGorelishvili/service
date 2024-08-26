package org.george.car.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.george.car.service.domain.UserEntity;
import org.george.car.service.domain.UserRole;
import org.george.car.service.domain.VehicleEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InitServiceBean implements InitService {

    private final UserService userService;

    @Override
    public List<UserEntity> initAdminUser() {

        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity adminUser = UserEntity.builder()
                .firstName("admin")
                .lastName("admin")
                .username("admin")
                .password("admin")
                .userRole(UserRole.ADMIN)
//                .vehicles(VehicleEntity.builder().vehicleServices(ArrayList).build())
                .build();
        adminUser = userService.saveUser(adminUser);
        log.info("Admin user created: {}", adminUser);
        userEntities.add(adminUser);
        return userEntities;
    }
}
