package org.desarrollo.dto;

public record TipoFiscalResponseDTO(
        Integer idTipoFiscal,
        String nombre,
        Boolean activo
) {
}
