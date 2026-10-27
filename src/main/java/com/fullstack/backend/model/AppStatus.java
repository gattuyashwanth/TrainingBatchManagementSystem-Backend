package com.fullstack.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "app_status")
public class AppStatus {

    @Id
    private String id;

    private String component;
    private String status;
    private Instant lastPing;

    public AppStatus() {
    }

    public AppStatus(String component, String status, Instant lastPing) {
        this.component = component;
        this.status = status;
        this.lastPing = lastPing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getLastPing() {
        return lastPing;
    }

    public void setLastPing(Instant lastPing) {
        this.lastPing = lastPing;
    }
}
