package org.george.car.service.service;

import org.george.car.service.domain.VehicleServiceEntity;

import java.util.List;

public interface VehicleServiceService {

    List<VehicleServiceEntity> vehicleServices(Integer userId, Integer vehicleId);

    VehicleServiceEntity createOrUpdateVehicleService(VehicleServiceEntity vehicleServiceEntity);

    List<VehicleServiceEntity> authUserVehicleServices(List<Integer> vehicleIds);
}
