package org.desarrollo.dto;

public record TipoEstablecimientoResponseDTO(
        Integer idTipoEstablecimiento,
        String tipo,
        Boolean activo
) {
}
