package org.desarrollo.dto;

public record MesaResponseDTO(
        Integer idMesa,
        Integer numeroMesa,
        Integer idEstablecimiento,
        String nombreEstablecimiento
) {
}
