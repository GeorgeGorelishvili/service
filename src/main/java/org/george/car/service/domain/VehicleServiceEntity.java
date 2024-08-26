package org.george.car.service.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VEHICLE_SERVICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
public class VehicleServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VEHICLE_SERVICE_ID")
    private Integer vehicleServiceId;

    @Column(name = "SERVICE_DESCRIPTION")
    private String serviceDescription;

    @Column(name = "serviceDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    private Date serviceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEHICLE_ID")
    private VehicleEntity vehicle;

}
