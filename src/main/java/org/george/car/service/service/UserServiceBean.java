package org.george.car.service.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.george.car.service.domain.UserEntity;
import org.george.car.service.domain.VehicleEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceBean implements UserService {

    private final EntityManager em;

    @Override
    public UserEntity findByUsername(String username) {

        UserEntity userEntity = null;

        try {
            userEntity = em.createQuery("select u from SYSTEM_USER u where u.username = :username", UserEntity.class)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException ex) {
            log.error("not found user by username: ", username, ex);
        }

        return userEntity;

    }

    @Override
    public List<UserEntity> init() {

        UserEntity user = UserEntity.builder()
                .firstName("firstName")
                .lastName("Lastname")
                .username("username")
                .password("password")
                .build();
        UserEntity savedUser = saveUser(user);
        List<UserEntity> users = new ArrayList<>();
        users.add(savedUser);
        return users;
    }

    @Override
    public List<UserEntity> all() {
        List<UserEntity> userEntityList = em.createQuery(
                "select u from UserEntity u", UserEntity.class).getResultList();

        return userEntityList;
    }

    @Override
    public List<UserEntity> find(UserEntity user) {
        List<UserEntity> userEntities = new ArrayList<>();
        StringBuilder sb = new StringBuilder("select u from UserEntity u where 1=1 ");
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNoneEmpty(user.getFirstName())) {
            sb.append(" and u.firstName like :firstName");
            params.put("firstName", user.getFirstName());
        }
        if (StringUtils.isNoneEmpty(user.getLastName())) {
            sb.append(" and u.lastName like :lastName");
            params.put("lastName", user.getLastName());
        }
        if (StringUtils.isNoneEmpty(user.getUsername())) {
            sb.append(" and u.username like :username");
            params.put("username", user.getUsername());
        }
        if (StringUtils.isNoneEmpty(user.getEmail())) {
            sb.append(" and u.email like :email");
            params.put("email", user.getEmail());
        }
        if (StringUtils.isNoneEmpty(user.getPhone())) {
            sb.append(" and u.phone like :phone");
            params.put("phone", user.getPhone());
        }

        TypedQuery<UserEntity> query = em.createQuery(sb.toString(), UserEntity.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public UserEntity saveUser(UserEntity user) throws EntityExistsException {
        UserEntity alreadyExists = findByUsername(user.getUsername());
        if (alreadyExists != null) {
            throw new EntityExistsException("Username already exists");
        }
        if (user.getUserId() > 0) {
            UserEntity loaded = em.find(UserEntity.class, user.getUserId());
            if (Objects.nonNull(loaded)) {
                user.setPassword(loaded.getPassword());
                user.setUsername(loaded.getUsername());
            }
        } else {
            user.setPassword("123456");
        }
        log.info("create user: {}", user);
        user = em.merge(user);
        return user;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public UserEntity updateVehicleEntity(Integer userId, Integer vehicleId) {
        UserEntity user = em.find(UserEntity.class, userId);
        user.getVehicles().add(VehicleEntity.builder().vehicleId(vehicleId).build());
        user = em.merge(user);
        return user;
    }
}
