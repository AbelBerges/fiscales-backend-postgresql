package org.desarrollo.dto;

public record MesaListaDTO(
        Integer idMesa,
        Integer numeroMesa,
        String establecimiento,
        String nombreFiscal,
        String apellidoFiscal,
        String basica
) {
}
