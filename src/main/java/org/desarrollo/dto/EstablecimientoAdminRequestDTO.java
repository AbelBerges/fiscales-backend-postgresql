package org.desarrollo.dto;

public record EstablecimientoAdminRequestDTO(
        String nombre,
        String descripcion,
        Integer idCalle,
        Integer altura,
        Integer idTipoPiso,
        Integer idTipoDepartamento,
        Integer idTipoEstablecimiento,
        Boolean activo,
        Integer idBasica
) {
}
