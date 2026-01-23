package org.desarrollo.dto;

public record EstablecimientosConMesasDTO(
        Integer idEstablecimiento,
        String nombreEstablecimiento,
        Long cantidadMesas,
        Integer desdeMesa,
        Integer hastaMesa
) {
}
