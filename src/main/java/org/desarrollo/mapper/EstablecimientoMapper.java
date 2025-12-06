package org.desarrollo.mapper;

import jakarta.persistence.EntityManager;
import org.desarrollo.dto.EstablecimientoRequestDTO;
import org.desarrollo.dto.EstablecimientoResponseDTO;
import org.desarrollo.model.*;

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
