package org.desarrollo.dto;

public record LoginResponse(
        String token,
        String rol,
        Integer idBasica
) {
}
