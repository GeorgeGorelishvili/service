package org.george.car.service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.george.car.service.domain.UserEntity;
import org.george.car.service.service.InitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController("/hello")
public class HelloController {

    private final InitService initService;

    @PostMapping("/init")
    public ResponseEntity init() {
        List<UserEntity> userEntities = initService.initAdminUser();
        return ResponseEntity.ok(userEntities);
    }


    @GetMapping("/say")
    public String say() {
        return "Hello World";
    }

    @GetMapping("/check")
    public String check() {
        return "Hello World";
    }
}
