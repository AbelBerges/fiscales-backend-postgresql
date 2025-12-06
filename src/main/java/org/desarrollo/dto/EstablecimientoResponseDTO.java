package org.desarrollo.dto;

public record EstablecimientoResponseDTO(
        Integer idEstablecimiento,
        String nombre,
        String descripcion,
        Integer idCalle,
        Integer altura,
        Integer idTipoPiso,
        Integer idTipoDepartamento,
        Integer idTipoEstablecimiento,
        Boolean activo
) {
}
