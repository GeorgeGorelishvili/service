package org.george.car.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.george.car.service.domain.UserEntity;
import org.george.car.service.domain.VehicleServiceEntity;
import org.george.car.service.service.UserService;
import org.george.car.service.service.VehicleService;
import org.george.car.service.service.VehicleServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController("/user")
public class UserController {

    private final UserService userService;
    private final VehicleServiceService vehicleServiceService;

    @GetMapping("/init")
    public List<UserEntity> init() {
        log.info("called init");
        return userService.init();
    }

    @PostMapping("/createOrUpdateUser")
    public ResponseEntity<UserEntity> createOrUpdateUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> findUser() {
        return ResponseEntity.ok(userService.all());
    }

    @PostMapping("/find")
    public ResponseEntity<List<UserEntity>> findUsers(@RequestBody UserEntity user) {
        List<UserEntity> userEntities = userService.find(user);
        log.info("find user: {}", user);
        return ResponseEntity.ok(userEntities);
    }

    @GetMapping("userVehicleServices")
    public ResponseEntity<List<VehicleServiceEntity>> getVehicleServices(@RequestParam Integer userId, @RequestParam Integer vehicleId) {

        List<VehicleServiceEntity> vehicleServiceEntities = vehicleServiceService.vehicleServices(userId, vehicleId);
        return ResponseEntity.ok(vehicleServiceEntities);
    }

}
