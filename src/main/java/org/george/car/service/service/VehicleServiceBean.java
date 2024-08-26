package org.george.car.service.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.george.car.service.domain.VehicleEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleServiceBean implements VehicleService {

    private final int pageSize = 50;
    private final EntityManager em;

    @Override
    public void deleteVehicle(Integer vehicleId) {
        em.createQuery("delete vs from VehicleServiceEntity vs where vs.vehicle.vehicleId = :vehicleId");
        em.remove(em.find(VehicleEntity.class, vehicleId));
    }

    @Override
    public List<VehicleEntity> searchVehicle(String govNumber, String vin, String manufacturer, String color,
                                             Date startDate, Date endDate, Integer firstResult) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder where = new StringBuilder(" where 1=1 ");

        if (StringUtils.isNotEmpty(govNumber)) {
            where.append(" and v.number LIKE '%:govNumber%'");
            params.put("govNumber", govNumber);
        }

        if (StringUtils.isNotEmpty(vin)) {
            where.append(" and v.vin LIKE '%:vin%'");
            params.put("vin", vin);
        }
        if (StringUtils.isNotEmpty(manufacturer)) {
            where.append(" and v.manufacturer LIKE '%:manufacturer%'");
            params.put("manufacturer", manufacturer);
        }
        if (StringUtils.isNotEmpty(color)) {
            where.append(" and v.color LIKE '%:color%'");
            params.put("color", color);
        }
        if (Objects.nonNull(startDate)) {
            where.append(" and v.serviceDate >= :startDate");
            params.put("startDate", startDate);
        }
        if (Objects.nonNull(endDate)) {
            where.append(" and v.serviceDate <= :endDate");
            params.put("endDate", endDate);
        }

        Integer objectsCount = em.createQuery("select count(v) from VehicleEntity v "
                + where.toString(), Integer.class).getSingleResult();
        Integer maxResult = pageSize;
        if (firstResult + pageSize > objectsCount) {
            maxResult = objectsCount - firstResult;
        }

        TypedQuery<VehicleEntity> q = em.createQuery("select v from VehicleEntity v " +
                where, VehicleEntity.class);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            q.setParameter(param.getKey(), param.getValue());
        }
        List<VehicleEntity> vehicles = q.setFirstResult(firstResult).setMaxResults(maxResult).getResultList();

        return vehicles;
    }

    @Override
    public List<VehicleEntity> getUserVehicles(Integer userId, boolean fetchVehicleServices) {
        List<VehicleEntity> list = em.createQuery(
                "select v from VehicleEntity v where v.user.userId = :userId", VehicleEntity.class)
                .setParameter("userId", userId).getResultList();
        if (fetchVehicleServices && !list.isEmpty()) {
            list.forEach(vehicleEntity -> vehicleEntity.getVehicleServices().size());
        }
        if (fetchVehicleServices && !list.isEmpty()) {
            list.forEach(vehicleEntity -> vehicleEntity.getVehicleServices().size());
        }
        log.info("getUserVehicles->userId: ", userId);
        return list;
    }

    @Override
    public VehicleEntity getUserVehicle(Integer userId, boolean fetchVehicleServices) {
        try {
            VehicleEntity vehicleEntity = em.createQuery(
                    "SELECT v FROM VehicleEntity v WHERE v.user_id = :userId", VehicleEntity.class)
                            .setParameter("userId", userId).getSingleResult();
            if (Objects.isNull(vehicleEntity)) {

            }
            if (fetchVehicleServices) {
                vehicleEntity.getVehicleServices().size();
            }
            return vehicleEntity;
        } catch (NoResultException e) {
            throw new NoResultException("Vehicle not found for userId: " + userId);
        }
    }

    @Override
    public List<VehicleEntity> getVehicles() {
        return em.createQuery("select v from VehicleEntity v", VehicleEntity.class).getResultList();
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public VehicleEntity createOrUpdateVehicle(VehicleEntity vehicle) {
        vehicle = em.merge(vehicle);
        log.info("Created vehicle: {}", vehicle);
        return vehicle;
    }

    @Override
    public List<VehicleEntity> authUserVehicles(Integer authUserId) {
        List<VehicleEntity> list = em.createQuery("select v from VehicleEntity v where v.user.userId = :userId", VehicleEntity.class)
                .setParameter("userId", authUserId).getResultList();
        return list;
    }

    @Override
    public List<Integer> authUserVehicleIds(Integer userId) {
        List<Integer> vehicleEntityIds = em.createQuery("select v.vehicleId from VehicleEntity v where v.user.userId = :userId", Integer.class)
                .setParameter("userId", userId).getResultList();
        return vehicleEntityIds;
    }
}
