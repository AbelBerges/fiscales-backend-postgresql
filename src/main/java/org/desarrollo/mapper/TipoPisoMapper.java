package org.desarrollo.mapper;

import org.desarrollo.dto.TipoPisoRequestDTO;
import org.desarrollo.dto.TipoPisoResponseDTO;
import org.desarrollo.model.TipoPiso;

public class TipoPisoMapper {

    public static TipoPiso aEntidadDeCreacion(TipoPisoRequestDTO dto) {
        TipoPiso tp = new TipoPiso(dto.nombre(), dto.activo());
        return tp;
    }

    public static TipoPisoResponseDTO aEntidadResponseDTO(TipoPiso tp) {
        return new TipoPisoResponseDTO(tp.getIdPiso(), tp.getNombre(), tp.isActivo());
    }

    public static void actualizarTipoPiso(TipoPisoRequestDTO dto, TipoPiso tp) {
        if (dto.nombre() != null) tp.setNombre(dto.nombre());
        if (dto.activo() != null) tp.setActivo(dto.activo());
    }


}
