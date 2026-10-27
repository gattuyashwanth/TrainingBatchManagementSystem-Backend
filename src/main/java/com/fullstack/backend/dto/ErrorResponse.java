package com.fullstack.backend.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        int status,
        String error,
        String message,
        List<String> details,
        String path,
        String timestamp
) {
    public static ErrorResponse of(int status, String error, String message, List<String> details, String path) {
        return new ErrorResponse(status, error, message, details, path, Instant.now().toString());
    }
}
