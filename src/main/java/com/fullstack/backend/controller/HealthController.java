package com.fullstack.backend.controller;

import com.fullstack.backend.dto.HealthResponse;
import com.fullstack.backend.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    private final HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    /**
     * Health check endpoint returning JSON status of the backend and database.
     * Accessible at GET http://localhost:8008/api/health
     */
    @GetMapping("/health")
    public ResponseEntity<HealthResponse> getHealth() {
        HealthResponse response = healthService.checkHealth();
        return ResponseEntity.ok(response);
    }
}
