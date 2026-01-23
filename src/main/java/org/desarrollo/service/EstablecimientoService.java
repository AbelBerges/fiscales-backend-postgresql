package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.desarrollo.config.ContextoBasica;
import org.desarrollo.config.ContextoUsuario;
import org.desarrollo.dto.*;
import org.desarrollo.mapper.EstablecimientoMapper;
import org.desarrollo.mapper.MesaMapper;
import org.desarrollo.model.Basica;
import org.desarrollo.model.Establecimiento;
import org.desarrollo.model.Mesa;
import org.desarrollo.repository.EstablecimientoRepository;
import org.desarrollo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository repo;
    @Autowired
    private EntityManager em;
    @Autowired
    private MesaRepository mesaRepo;


    public List<EstablecimientoListaDTO> listarTodos() {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.buscarEstablecimientoOptimizado();
        }
        return repo.buscarEstablecimientoOptimizado(ContextoBasica.get());
    }

    public EstablecimientoResponseDTO buscarPorId(Integer id) {
        return repo.findById(id)
                .map(EstablecimientoMapper::aEntidadEstablecimientoResponseDTO)
                .orElse(null);
    }

    public EstablecimientoListaDTO buscarUnoPorId(Integer id) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.unoOptimizado(id);
        }
        return repo.unoOptimizado(id, ContextoBasica.get());
    }

    public String BuscarTipoEstablecimientoPorId(Integer idEstablecimiento) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.obtenerTipoEstablecimientoPorIdEstablecimiento(idEstablecimiento);
        }
        return repo.obtenerTipoEstablecimientoPorIdEstablecimiento(idEstablecimiento, ContextoBasica.get());
    }

    public String recuperoEstadoEstablecimiento(Integer idEst) {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.estadoEstablecimiento(idEst);
        }
        return repo.estadoEstablecimiento(idEst, ContextoBasica.get());
    }

    public List<EstablecimientoResponseDTO> listaPornombre(String nombre) {
        return repo.findByNombreEstablecimientoContainingIgnoreCaseAndBasica_IdBasica(nombre, ContextoBasica.get()).stream()
                .map(EstablecimientoMapper::aEntidadEstablecimientoResponseDTO).toList();
    }

    public EstablecimientoDetalleEstadoDTO listadoDelEstadoEstablecimientos(Integer idEst) {
        if (ContextoUsuario.esAdminGlobal()) {
            //Vamos a buscar el estado actual del establecimiento
            String estadoEst = recuperoEstadoEstablecimiento(idEst);
            //Buscamos el estado de las mesas
            List<Object[]> crudoMesas = mesaRepo.estadoCrudoMesas(idEst);
            //Mapeamos las mesas al formato esperado por el frontend
            List<MesaEstadoDTO> mesas = MesaMapper.aMesaDesdeObjetc(crudoMesas);
            return new EstablecimientoDetalleEstadoDTO(idEst, estadoEst, mesas);

        } else {
            Integer idBasica = ContextoBasica.get();
            //Buscamos el estado del establecimiento
            String estadoEst = recuperoEstadoEstablecimiento(idEst);
            //Buscamos el estado de las mesas Objetc[]
            List<Object[]> crudoMesas = mesaRepo.estadoCrudoMesas(idEst, idBasica);
            //Mapeamos las mesas
            List<MesaEstadoDTO> mesas = MesaMapper.aMesaDesdeObjetc(crudoMesas);
            return new EstablecimientoDetalleEstadoDTO(
                    idEst,
                    estadoEst,
                    mesas
            );
        }

    }

    public List<EstablecimientoEstadoDTO> listarEstadosParaCombo() {
        if (ContextoUsuario.esAdminGlobal()) {
            List<Object[]> estados = repo.estadoEstablecimientos();
            return EstablecimientoMapper.mapearDesdeListaObjet(estados);
        }
        Integer idBasica = ContextoUsuario.getBasica();
        List<Object[]> estados = repo.estadoEstablecimientos(idBasica);
        return EstablecimientoMapper.mapearDesdeListaObjet(estados);
    }

    @Transactional
    public EstablecimientoListaDTO guardar(EstablecimientoRequestDTO dto) {
        if (repo.existsByNombreEstablecimiento(dto.nombre())) {
            throw new IllegalArgumentException("Ya existe el nombre de ese establecimiento");
        }
        Integer idBasica;
        if (ContextoUsuario.esAdminGlobal()) {
            throw new IllegalStateException(
                    "ADMIN_GLOBAL usa otro endpoint exclusivo para su uso"
            );
        }
        idBasica = ContextoUsuario.getBasica();
        Establecimiento nuevo = EstablecimientoMapper.aEntidadCreacion(dto, em);
        nuevo.setBasica(em.getReference(Basica.class, idBasica));
        Establecimiento guardado = repo.save(nuevo);
        return EstablecimientoMapper.desdeEstablecimientoAListaDTO(guardado);
    }

    @Transactional
    public EstablecimientoListaDTO guardarComoAdmin(EstablecimientoRequestDTO dto, Integer idBasica) {
        if (repo.existsByNombreEstablecimiento(dto.nombre())) {
            throw new IllegalArgumentException("Ya existe un establecimiento con ese nombre");
        }
        if (!ContextoUsuario.esAdminGlobal()) {
            throw new IllegalStateException("No está autorizado");
        }
        Establecimiento nuevo = EstablecimientoMapper.aEntidadCreacion(dto, em);
        nuevo.setBasica(em.getReference(Basica.class, idBasica));
        Establecimiento guardado = repo.save(nuevo);
        return EstablecimientoMapper.desdeEstablecimientoAListaDTO(guardado);
    }

    @Transactional
    public boolean actualizo(Integer id, EstablecimientoRequestDTO dto) {
        return repo.findById(id).map(existe -> {
            if (!dto.nombre().equals(existe.getNombreEstablecimiento()) && repo.existsByNombreEstablecimiento(dto.nombre())) {
                throw new IllegalArgumentException("Ya existe otro establecimiento con ese nombre");
            }
            if (!ContextoUsuario.esAdminGlobal()) {
                Integer basicaUsuario = ContextoUsuario.getBasica();
                if (!Objects.equals(existe.getBasica().getIdBasica(), basicaUsuario)) {
                    throw new SecurityException("No puede modificar establecimientos de otras básicas");
                }
            }
            EstablecimientoMapper.paraActualizarEstablecimiento(dto, existe, em);
            repo.save(existe);
            return true;
        }).orElse(false);
    }

    @Transactional
    public void cambiarBasica(Integer idEst, Integer idBasica) {
        if (!ContextoUsuario.esAdminGlobal()) {
            throw new SecurityException("No tiene permisos para cambiar la básica");
        }
        Establecimiento est = repo.findById(idEst).orElseThrow(() -> new EntityNotFoundException("No existe el establecimiento"));
        if (!est.getMesas().isEmpty()) {
            throw new IllegalStateException("No se pueda cambiar el establecimiento porque tiene mesas asignadas");
        }
        if (!est.getFiscales().isEmpty()) {
            throw new IllegalStateException("No se puede cambiar el establecimiento porque tiene fiscales asignados");
        }
        est.setBasica(em.getReference(Basica.class, idBasica));
        repo.save(est);
    }

    @Transactional
    public boolean desactivar(Integer id) {
        return repo.findById(id).map(existe -> {
            if (!existe.getMesas().isEmpty()) {
                throw new SecurityException("No se puede desactivar el establecimiento - Tiene mesas asignadas");
            }
            if (existe.getFiscales().isEmpty()) {
                throw new SecurityException("No se puede desactivar el establecimiento - Tiene fiscales asignados");
            }
            if (!ContextoUsuario.esAdminGlobal()) {
                if (!Objects.equals(ContextoUsuario.getBasica(), existe.getBasica().getIdBasica())) {
                    throw new SecurityException("No puede desactivar un establecimiento de otra básica");
                }
            }
            existe.setActivo(false);
            repo.save(existe);
            return true;
        }).orElse(false);
    }

    public List<EstablecimientoResponseDTO> listaEstablecimientosSinMesa() {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findEstablecimientosSinMesas()
                    .stream()
                    .map(EstablecimientoMapper::aEntidadEstablecimientoResponseDTO)
                    .toList();
        }
        return repo.findEstablecimientosSinMesasAndBasica_IdBasica(ContextoBasica.get()).stream()
                .map(EstablecimientoMapper::aEntidadEstablecimientoResponseDTO)
                .toList();
    }

    public List<EstablecimientosConMesasDTO> listaEstablecimientoConMesas() {
        if (ContextoUsuario.esAdminGlobal()) {
            return repo.findEstablecimientoConMesas()
                    .stream()
                    .toList();
        }
        return repo.findEstablecimientoConMesasAndBasica_IdBasica(ContextoBasica.get()).stream()
                .toList();
    }

    public EstablecimientosConMesasDTO mesasResumen(Integer idEstablecimiento){
        if (ContextoUsuario.esAdminGlobal()) {
            List<Mesa> listado = mesaRepo.findByEstablecimiento_IdEstablecimiento(idEstablecimiento);
            EstablecimientoResponseDTO temp = buscarPorId(idEstablecimiento);
            if (listado.isEmpty()) {
                Long cantidad = (long) (int) 0;
                return new EstablecimientosConMesasDTO(
                        idEstablecimiento,
                        temp.nombre(),
                        cantidad,
                        null,
                        null
                );
            }
            Long cant = (long) (int) listado.size();
            return new EstablecimientosConMesasDTO(
                    idEstablecimiento,
                    temp.nombre(),
                    cant,
                    listado.get(0).getNumeroMesa(),
                    listado.get(listado.size() -1).getNumeroMesa()
            );
        } else {
            List<Mesa> listado = mesaRepo.findByEstablecimiento_IdEstablecimientoAndBasica_IdBasica(idEstablecimiento, ContextoBasica.get());
            EstablecimientoResponseDTO temp = buscarPorId(idEstablecimiento);
            if (listado.isEmpty()) {
                Long cantidad = (long) (int) 0;
                return new EstablecimientosConMesasDTO(
                        idEstablecimiento,
                        temp.nombre(),
                        cantidad,
                        null,
                        null
                );
            }
            Long cant = (long) (int) listado.size();
            return new EstablecimientosConMesasDTO(
                    idEstablecimiento,
                    temp.nombre(),
                    cant,
                    listado.get(0).getNumeroMesa(),
                    listado.get(listado.size() -1).getNumeroMesa()
            );
        }

    }
}
