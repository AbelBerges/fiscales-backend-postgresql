package org.desarrollo.dto;

import org.desarrollo.model.Calle;
import org.desarrollo.model.TipoDepartamento;
import org.desarrollo.model.TipoPiso;

public record DireccionResponseDTO(
        Integer idDireccion,
        Integer calle,
        Integer altura,
        Integer tipoPiso,
        Integer tipoDepartamento
) {
}
