package org.desarrollo.dto;

public record LoginRequest(
        String usuario,
        String password
) {
}
