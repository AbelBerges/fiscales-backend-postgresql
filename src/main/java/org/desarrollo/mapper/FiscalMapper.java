package org.desarrollo.mapper;

import jakarta.persistence.EntityManager;
import org.desarrollo.dto.FiscalRequestDTO;
import org.desarrollo.dto.FiscalResponseDTO;
import org.desarrollo.model.*;

import java.util.Objects;

public class FiscalMapper {

    public static Fiscal aEntidadDeCreacion(FiscalRequestDTO dto, EntityManager em) {
        Fiscal nuevo = new Fiscal();
        nuevo.setNombreFiscal(dto.nombreFiscal());
        nuevo.setApellidoFiscal(dto.apellidoFiscal());
        nuevo.setEdadFiscal(dto.edad());
        nuevo.setCorreoFiscal(dto.correoFiscal());
        nuevo.setTelefono(dto.telefono());
        if (dto.idTipoFiscal() != null) {
            nuevo.setTipoFiscal(em.getReference(TipoFiscal.class, dto.idTipoFiscal()));
        }
        if (dto.activo() != null) {
            nuevo.setActivo(dto.activo());
        }

        if (dto.idEstablecimientoVotacion() != null) {
            nuevo.setEstablecimientoVoto(em.getReference(Establecimiento.class, dto.idEstablecimientoVotacion()));
        }
        //si tiene dirección la configuramos
        if (dto.idCalle() != null) {
            Direccion dir = new Direccion();
            dir.setCalle(em.getReference(Calle.class, dto.idCalle()));
            dir.setAltura(dto.altura());
            if (dto.idTipoPiso() != null) {
                dir.setTipoPiso(em.getReference(TipoPiso.class, dto.idTipoPiso()));
            }
            if (dto.idTipoDepartamento() != null) {
                dir.setTipoDepartamento(em.getReference(TipoDepartamento.class, dto.idTipoDepartamento()));
            }
            nuevo.setDireccion(dir);
        }
        if (dto.idJornada() != null) {
            nuevo.setJornada(em.getReference(Jornada.class, dto.idJornada()));
        }
        // Lo más seguro es que no asignemos el fiscal a la mesa en la creación. Pero por las dudas...
        if (dto.idMesa() != null) {
            //Aquí no hacemos validaciones en el mapper. El servicio tiene que hacerlo
            nuevo.setMesa(em.getReference(Mesa.class, dto.idMesa()));
        }
        return nuevo;
    }


    public static FiscalResponseDTO aEntidadResponseDTO(Fiscal fiscal) {
        return new FiscalResponseDTO(
                fiscal.getIdFiscal(),
                fiscal.getNombreFiscal(),
                fiscal.getApellidoFiscal(),
                fiscal.getEdadFiscal(),
                fiscal.getCorreoFiscal(),
                fiscal.getTelefono(),
                fiscal.getTipoFiscal().getIdTipoFiscal(),
                fiscal.isActivo(),
                fiscal.getEstablecimientoVoto() != null ? fiscal.getEstablecimientoVoto().getIdEstablecimiento() : null,
                fiscal.getDireccion().getCalle() != null ? fiscal.getDireccion().getCalle().getIdCalle() : null,
                fiscal.getDireccion().getAltura(),
                fiscal.getDireccion().getTipoPiso() != null ? fiscal.getDireccion().getTipoPiso().getIdPiso() : null,
                fiscal.getDireccion().getTipoDepartamento() != null ? fiscal.getDireccion().getTipoDepartamento().getIdDepartamento() : null,
                fiscal.getJornada().getIdJornada() != null ? fiscal.getJornada().getIdJornada() : null,
                fiscal.getMesa() != null ? fiscal.getMesa().getIdMesa() : null

        );
    }

    public static void paraActualizarFiscal(FiscalRequestDTO dto, Fiscal fiscal, EntityManager em) {
        if (dto.nombreFiscal() != null) fiscal.setNombreFiscal(dto.nombreFiscal());
        if (dto.apellidoFiscal() != null) fiscal.setApellidoFiscal(dto.apellidoFiscal());
        if (dto.edad() != null) fiscal.setEdadFiscal(dto.edad());
        if (dto.correoFiscal() != null) fiscal.setCorreoFiscal(dto.correoFiscal());
        if (dto.telefono() != null) fiscal.setTelefono(dto.telefono());
        if (dto.idTipoFiscal() != null && !Objects.equals(fiscal.getTipoFiscal().getIdTipoFiscal(), dto.idTipoFiscal())) {
            fiscal.setTipoFiscal(em.getReference(TipoFiscal.class, dto.idTipoFiscal()));
        }
        if (dto.activo() != null) fiscal.setActivo(dto.activo());
        if (dto.idEstablecimientoVotacion() != null  && !Objects.equals(fiscal.getEstablecimientoVoto().getIdEstablecimiento(), dto.idEstablecimientoVotacion())) {
            fiscal.setEstablecimientoVoto(em.getReference(Establecimiento.class, dto.idEstablecimientoVotacion()));
        }
        //if (dto.direccion() != null) fiscal.setDireccion(dto.direccion());
        Direccion dir = fiscal.getDireccion();
        if (dto.idCalle() != null && !Objects.equals(dto.idCalle(), fiscal.getDireccion().getCalle().getIdCalle())) {
            dir.setCalle(em.getReference(Calle.class, dto.idCalle()));
        }
        if (dto.altura() != null) {
            dir.setAltura(dto.altura());
        }
        if (dto.idTipoPiso() != null && !Objects.equals(dto.idTipoPiso(), fiscal.getDireccion().getTipoPiso().getIdPiso())) {
            dir.setTipoPiso(em.getReference(TipoPiso.class, dto.idTipoPiso()));
        }
        if (dto.idTipoDepartamento() != null && !Objects.equals(dto.idTipoDepartamento(), fiscal.getDireccion().getTipoDepartamento().getIdDepartamento())) {
            dir.setTipoDepartamento(em.getReference(TipoDepartamento.class, dto.idTipoDepartamento()));
        }
        //Jornada
        if (dto.idJornada() != null && !Objects.equals(dto.idJornada(), fiscal.getJornada().getIdJornada())) {
            fiscal.setJornada(em.getReference(Jornada.class, dto.idJornada()));
        }
        // Mesa: setear o liberar. El servicio debe validar disponibilidad
        if (dto.idMesa() != null) {
            fiscal.setMesa(em.getReference(Mesa.class, dto.idMesa()));
        } else {
            fiscal.setMesa(null);
        }
        //fiscal.setDireccion(dir);

    }

}
