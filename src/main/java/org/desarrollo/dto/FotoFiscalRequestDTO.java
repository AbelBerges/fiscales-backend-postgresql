package org.desarrollo.dto;

import org.desarrollo.model.Fiscal;

public record FotoFiscalRequestDTO(
        byte[] imagen,
        Integer idFiscal
) {
}
