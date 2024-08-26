package org.george.car.service.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VEHICLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VEHICLE_ID")
    private Integer vehicleId;

    @Column(name = "GOV_NUMBER", columnDefinition = "varchar(20)", length = 20)
    private String govNumber;

    @Column(name = "VIN", columnDefinition = "varchar(50)", length = 50)
    private String vin;

    @Column(name = "MANUFACTURER", columnDefinition = "varchar(50)", length = 50)
    private String manufacturer;

    @Column(name = "COLOR", columnDefinition = "nvarchar(50)", length = 50)
    private String color;

    @Column(name = "SERVICE_DATE", columnDefinition = "")
    private Date serviceDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEHICLE_SERVICE_ID")
    private List<VehicleServiceEntity> vehicleServices;

}
