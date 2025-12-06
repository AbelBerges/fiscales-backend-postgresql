package org.desarrollo.dto;

import org.desarrollo.model.Fiscal;

public record FotoFiscalResponseDTO(
        Integer idFoto,
        byte[] imagen,
        Integer idFiscal
) {
}
