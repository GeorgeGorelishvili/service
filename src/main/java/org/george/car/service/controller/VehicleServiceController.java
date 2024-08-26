package org.george.car.service.controller;

import lombok.RequiredArgsConstructor;
import org.george.car.service.domain.VehicleServiceEntity;
import org.george.car.service.service.VehicleService;
import org.george.car.service.service.VehicleServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController("/vehicleServices")
public class VehicleServiceController {

    private final VehicleService vehicleService;
    private final VehicleServiceService vehicleServiceService;

    @PostMapping("/createOrUpdateVehicleService")
    public VehicleServiceEntity createOrUpdateVehicleService(@RequestBody VehicleServiceEntity vehicleServiceEntity) {
        return vehicleServiceService.createOrUpdateVehicleService(vehicleServiceEntity);
    }

    @GetMapping("/vehicleServices")
    public List<VehicleServiceEntity> getVehicleServices(@RequestParam Integer userId, @RequestParam Integer vehicleId) {
        return vehicleServiceService.vehicleServices(userId, vehicleId);
    }

    @GetMapping("/authUserVehicleServices")
    public List<VehicleServiceEntity> getAuthUserVehicleServices(@RequestParam Integer userId) {
        List<Integer> vehicleIds = vehicleService.authUserVehicleIds(userId);
        List<VehicleServiceEntity> vehicleServiceEntities = vehicleServiceService.authUserVehicleServices(vehicleIds);
        return vehicleServiceEntities;
    }
}
