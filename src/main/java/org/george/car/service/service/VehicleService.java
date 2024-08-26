package org.george.car.service.service;

import org.george.car.service.domain.VehicleEntity;

import java.util.Date;
import java.util.List;

public interface VehicleService {

    void deleteVehicle(Integer vehicleId);

    List<VehicleEntity> searchVehicle(String govNumber, String vin, String manufacturer, String color,
                                      Date startDate, Date endDate, Integer firstResult);

    List<VehicleEntity> getUserVehicles(Integer userId, boolean fetchVehicleServices);

    VehicleEntity getUserVehicle(Integer userId, boolean fetchVehicleServices);

    List<VehicleEntity> getVehicles();

    VehicleEntity createOrUpdateVehicle(VehicleEntity vehicle);

    List<VehicleEntity> authUserVehicles(Integer authUserId);

    List<Integer> authUserVehicleIds(Integer userId);

}
