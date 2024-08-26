package org.george.car.service.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "SYSTEM_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "FIRST_NAME", columnDefinition = "nvarchar(50)", length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", columnDefinition = "nvarchar(50)", length = 50)
    private String lastName;

    @Column(name = "USERNAME", columnDefinition = "nvarchar(50)", length = 50)
    private String username;

    @Column(name = "EMAIL", columnDefinition =  "nvarchar(50)")
    private String email;

    @Column(name = "PHONE", columnDefinition = "nvarchar(50)", length = 50)
    private String phone;

    @Column(name = "PASSWORD", columnDefinition = "nvarchar(200)", length = 200)
    private String password;

    @Column(name = "ROLE", columnDefinition = "nvarchar(20)", length = 20)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<VehicleEntity> vehicles;

}
