package org.desarrollo.dto;

import org.desarrollo.model.Calle;
import org.desarrollo.model.TipoDepartamento;
import org.desarrollo.model.TipoPiso;

public record DireccionRequestDTO(
        Integer idCalle,
        Integer altura,
        Integer idTipoPiso,
        Integer idTipoDepartamento
) {
}
