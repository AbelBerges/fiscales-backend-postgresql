package org.desarrollo.mapper;

import org.desarrollo.dto.TipoEstablecimientoRequestDTO;
import org.desarrollo.dto.TipoEstablecimientoResponseDTO;
import org.desarrollo.model.TipoEstablecimiento;

public class TipoEstablecimientoMapper {

    public static TipoEstablecimiento aEntidadDeCreacion(TipoEstablecimientoRequestDTO dto) {
        TipoEstablecimiento te = new TipoEstablecimiento(dto.tipo(), dto.activo());
        return te;
    }

    public static TipoEstablecimientoResponseDTO aEntidadResponseDTO(TipoEstablecimiento tpe) {
        return new TipoEstablecimientoResponseDTO(
                tpe.getIdTipoEstablecimiento(),
                tpe.getTipo(),
                tpe.isActivo()
        );
    }

    public static void paraActualizarTipoEstablecimiento(TipoEstablecimientoRequestDTO dto, TipoEstablecimiento tpe) {
        if (dto.tipo() != null) tpe.setTipo(dto.tipo());
        if (dto.activo() != null) tpe.setActivo(dto.activo());
    }
}
