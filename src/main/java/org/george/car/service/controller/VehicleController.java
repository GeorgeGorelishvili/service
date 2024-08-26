package org.george.car.service.controller;

import lombok.RequiredArgsConstructor;
import org.george.car.service.domain.UserEntity;
import org.george.car.service.domain.VehicleEntity;
import org.george.car.service.service.UserService;
import org.george.car.service.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController("/vehicle")
public class VehicleController {

    private final UserService userService;
    private final VehicleService vehicleService;

    @PostMapping("/createOrUpdateVehicle")
    public VehicleEntity createOrUpdateVehicle(@RequestBody VehicleEntity vehicle) {
        vehicle.setUser(UserEntity.builder().userId(vehicle.getUser().getUserId()).build());
        VehicleEntity vehicleEntity = vehicleService.createOrUpdateVehicle(vehicle);
//        userService.updateVehicleEntity(vehicle.getUser().getUserId(), vehicle.getVehicleId());
        return vehicleEntity;
    }

    @GetMapping("/delete")
    public ResponseEntity deleteVehicle(@RequestParam Integer vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/search")
    public List<VehicleEntity> search(@RequestParam(required = false) String govNumber, @RequestParam(required = false) String vin,
                                      @RequestParam(required = false) String manufacturer, @RequestParam(required = false) String color,
                                      @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
                                      @RequestParam int firstResult) {
        List<VehicleEntity> vehicles= vehicleService.searchVehicle(govNumber, vin, manufacturer, color, startDate, endDate, firstResult);
        return vehicles;
    }

    @GetMapping("/findAll")
    public List<VehicleEntity> getVehicles() {
        return vehicleService.getVehicles();
    }

    @GetMapping("/userVehicles")
    public List<VehicleEntity> userVehicles(@RequestParam Integer userId) {
        return vehicleService.getUserVehicles(userId, Boolean.FALSE);
    }
}
