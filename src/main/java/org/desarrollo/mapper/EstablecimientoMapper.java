package org.desarrollo.mapper;

import jakarta.persistence.EntityManager;
import org.desarrollo.dto.*;
import org.desarrollo.model.*;

import java.util.List;
import java.util.Objects;

public class EstablecimientoMapper {


    public static Establecimiento aEntidadCreacion(EstablecimientoRequestDTO dto, EntityManager em) {
        Establecimiento est = new Establecimiento();

        est.setNombreEstablecimiento(dto.nombre());
        if (dto.descripcion() != null) {
            est.setDescripcion(dto.descripcion());
        }
        est.setActivo(dto.activo());

        Direccion dir = new Direccion();
        dir.setCalle(em.getReference(Calle.class, dto.idCalle()));
        dir.setAltura(dto.altura());
        if (dto.idTipoPiso() != null) {
            dir.setTipoPiso(em.getReference(TipoPiso.class, dto.idTipoPiso()));
        }
        if (dto.idTipoDepartamento() != null) {
            dir.setTipoDepartamento(em.getReference(TipoDepartamento.class, dto.idTipoDepartamento()));
        }
        est.setTipoEstablecimiento(em.getReference(TipoEstablecimiento.class, dto.idTipoEstablecimiento()));

        est.setDireccion(dir);
        return est;
    }

    public static List<EstablecimientoEstadoDTO> mapearDesdeListaObjet(List<Object[]> listado) {
        List<EstablecimientoEstadoDTO> lista = listado.stream()
                .map(f -> new EstablecimientoEstadoDTO(
                        ((Number) f[0]).intValue(),
                        (String) f[1],
                        (String) f[2]
                ))
                .toList();
        return lista;
    }

    public static EstablecimientoListaDTO desdeEstablecimientoAListaDTO(Establecimiento est) {
        Long cantidad = null;
        if (est.getMesas().isEmpty()) {
            cantidad = (long) est.getMesas().size();
        }
        String piso = est.getDireccion() != null && est.getDireccion().getTipoPiso() != null
                ? est.getDireccion().getTipoPiso().getNombre()
                : null;
        String departamento = est.getDireccion() != null && est.getDireccion().getTipoDepartamento() != null
                ? est.getDireccion().getTipoDepartamento().getNombre()
                : null;
        return new EstablecimientoListaDTO(
                est.getIdEstablecimiento(),
                est.getNombreEstablecimiento(),
                est.getDescripcion(),
                cantidad,
                est.getDireccion().getCalle().getNombre(),
                est.getDireccion().getAltura(),
                piso,
                departamento,
                est.getTipoEstablecimiento().getTipo(),
                est.isActivo()
        );
    }

    public static EstablecimientoResponseDTO aEntidadEstablecimientoResponseDTO(Establecimiento establecimiento) {
        return new EstablecimientoResponseDTO(
                establecimiento.getIdEstablecimiento(),
                establecimiento.getNombreEstablecimiento(),
                establecimiento.getDescripcion(),
                establecimiento.getDireccion().getCalle().getIdCalle(),
                establecimiento.getDireccion().getAltura(),
                establecimiento.getDireccion().getTipoPiso() != null ? establecimiento.getDireccion().getTipoPiso().getIdPiso() : null,
                establecimiento.getDireccion().getTipoDepartamento() != null ?establecimiento.getDireccion().getTipoDepartamento().getIdDepartamento() : null,
                establecimiento.getTipoEstablecimiento().getIdTipoEstablecimiento(),
                establecimiento.isActivo()
        );
    }

    public static void paraActualizarEstablecimiento(EstablecimientoRequestDTO dto, Establecimiento existente, EntityManager entityManager) {
        if (dto.nombre() != null) {
            existente.setNombreEstablecimiento(dto.nombre());
        }
        if (dto.descripcion() != null) {
            existente.setDescripcion(dto.descripcion());
        }
        if (dto.activo() != null) {
            existente.setActivo(dto.activo());
        }

        //Tipo de establecimiento
        if (dto.idTipoEstablecimiento() != null && !Objects.equals(existente.getTipoEstablecimiento().getIdTipoEstablecimiento(), dto.idTipoEstablecimiento())) {
            existente.setTipoEstablecimiento(entityManager.getReference(TipoEstablecimiento.class, dto.idTipoEstablecimiento()));
        }

        //Direccion temporal para evaluar si viene y es distinta
        Direccion dir = existente.getDireccion();
        if (dto.idCalle() != null && !Objects.equals(dto.idCalle(), existente.getDireccion().getCalle().getIdCalle())) {
            dir.setCalle(entityManager.getReference(Calle.class, dto.idCalle()));
        }
        if (dto.altura() != null) {
            dir.setAltura(dto.altura());
        } else {
            throw new IllegalArgumentException("La altura no puede estar vacía");
        }
        if (dto.idTipoPiso() != null) {
            if (!Objects.equals(dto.idTipoPiso(), existente.getDireccion().getTipoPiso().getIdPiso())) {
                dir.setTipoPiso(entityManager.getReference(TipoPiso.class, dto.idTipoPiso()));
            }
        }
        if (dto.idTipoDepartamento() != null) {
            if (!Objects.equals(dto.idTipoDepartamento(), existente.getDireccion().getTipoDepartamento().getIdDepartamento())){
                dir.setTipoDepartamento(entityManager.getReference(TipoDepartamento.class, dto.idTipoDepartamento()));
            }
        }

        //existente.setDireccion(dir);

    }

}
