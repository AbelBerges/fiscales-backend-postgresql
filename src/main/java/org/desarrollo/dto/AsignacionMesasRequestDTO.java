package org.desarrollo.dto;

import java.util.List;

public record AsignacionMesasRequestDTO(
        Integer idEstablecimiento,
        List<Integer> numerosMesa
) {
}
