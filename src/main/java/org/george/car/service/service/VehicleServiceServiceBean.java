package org.george.car.service.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.george.car.service.domain.VehicleServiceEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleServiceServiceBean implements VehicleServiceService {

    private final EntityManager em;

    @Override
    public List<VehicleServiceEntity> vehicleServices(Integer userId, Integer vehicleId) {
        List<VehicleServiceEntity> vehicleServices = new ArrayList<>();

        if (Objects.nonNull(userId) || Objects.nonNull(vehicleId)) {
            //StringBuilder countQueryStart = new StringBuilder("select count(v) from VehicleEntity v where 1=1 ");
            //StringBuilder count = new StringBuilder("SELECT v FROM VehicleServiceEntity v WHERE 1=1");
            StringBuilder sb = new StringBuilder("select count(v) from VehicleEntity v where 1=1");
            Map<String, Object> params = new HashMap<>();

            if (userId != null) {
                sb.append(" AND v.userId = :userId ");
                params.put("userId", userId);
            }

            if (vehicleId != null) {
                sb.append(" AND v.vehicleId = :vehicleId ");
                params.put("vehicleId", vehicleId);
            }

            TypedQuery<VehicleServiceEntity> query = em.createQuery(sb.toString(), VehicleServiceEntity.class);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            vehicleServices = query.getResultList();
        }
        return vehicleServices;
    }

    @Override
    public VehicleServiceEntity createOrUpdateVehicleService(VehicleServiceEntity vehicleServiceEntity) {
        vehicleServiceEntity = em.merge(vehicleServiceEntity);
        log.info("{} vehicle service {}", Objects.isNull(vehicleServiceEntity.getVehicleServiceId()) ? " Created" : " Updated", vehicleServiceEntity);
        return vehicleServiceEntity;
    }

    @Override
    public List<VehicleServiceEntity> authUserVehicleServices(List<Integer> vehicleIds) {
        return em.createQuery("select vs from VehicleServiceEntity vs where vs.vehicle.vehicleId in :vehicleIds", VehicleServiceEntity.class)
                .setParameter("vehicleIds", vehicleIds).getResultList();
    }
}
