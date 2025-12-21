package org.desarrollo.dto;

import java.util.List;

public record EstablecimientoDetalleEstadoDTO(
        Integer idEstablecimiento,
        String estadoEstablecimiento,
        List<MesaEstadoDTO> mesas
) {
}
