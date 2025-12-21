package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.desarrollo.dto.*;
import org.desarrollo.mapper.EstablecimientoMapper;
import org.desarrollo.mapper.MesaMapper;
import org.desarrollo.model.Establecimiento;
import org.desarrollo.model.Mesa;
import org.desarrollo.repository.EstablecimientoRepository;
import org.desarrollo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository repo;
    @Autowired
    private EntityManager em;
    @Autowired
    private MesaRepository mesaRepo;


    public List<EstablecimientoListaDTO> listarTodos() {
        return repo.buscarEstablecimientoOptimizado();
    }

    public EstablecimientoResponseDTO buscarPorId(Integer id) {
        return repo.findById(id)
                .map(EstablecimientoMapper::aEntidadEstablecimientoResponseDTO)
                .orElse(null);
    }

    public EstablecimientoListaDTO buscarUnoPorId(Integer id) {
        return repo.unoOptimizado(id);
    }

    public String BuscarTipoEstablecimientoPorId(Integer idEstablecimiento) {
        return repo.obtenerTipoEstablecimientoPorIdEstablecimiento(idEstablecimiento);
    }

    public String recuperoEstadoEstablecimiento(Integer idEst) {
        return repo.estadoEstablecimiento(idEst);
    }

    public List<EstablecimientoResponseDTO> listaPornombre(String nombre) {
        return repo.findByNombreEstablecimientoContainingIgnoreCase(nombre).stream()
                .map(EstablecimientoMapper::aEntidadEstablecimientoResponseDTO).toList();
    }

    public EstablecimientoDetalleEstadoDTO listadoDelEstadoEstablecimientos(Integer idEst) {
        //Buscamos el estado del establecimiento
        String estadoEst = recuperoEstadoEstablecimiento(idEst);
        //Buscamos el estadod de las mesas Objetc[]
        List<Object[]> crudoMesas = mesaRepo.estadoCrudoMesas(idEst);
        //Mapeamos las mesas
        List<MesaEstadoDTO> mesas = MesaMapper.aMesaDesdeObjetc(crudoMesas);
        return new EstablecimientoDetalleEstadoDTO(
                idEst,
                estadoEst,
                mesas
        );
    }

    public List<EstablecimientoEstadoDTO> listarEstadosParaCombo() {
        List<Object[]> estados = repo.estadoEstablecimientos();
        return EstablecimientoMapper.mapearDesdeListaObjet(estados);
    }

    @Transactional
    public EstablecimientoListaDTO guardar(EstablecimientoRequestDTO dto) {
        Establecimiento nuevo = EstablecimientoMapper.aEntidadCreacion(dto, em);
        Establecimiento guardado = repo.save(nuevo);
        return EstablecimientoMapper.desdeEstablecimientoAListaDTO(guardado);
    }

    @Transactional
    public boolean actualizo(Integer id, EstablecimientoRequestDTO dto) {
        return repo.findById(id).map(existe -> {
            EstablecimientoMapper.paraActualizarEstablecimiento(dto, existe, em);
            repo.save(existe);
            return true;
        }).orElse(false);
    }

    @Transactional
    public boolean desactivar(Integer id) {
        return repo.findById(id).map(existe -> {
            existe.setActivo(false);
            repo.save(existe);
            return true;
        }).orElse(false);
    }

    public List<EstablecimientoResponseDTO> listaEstablecimientosSinMesa() {
        return repo.findEstablecimientosSinMesas().stream()
                .map(EstablecimientoMapper::aEntidadEstablecimientoResponseDTO)
                .toList();
    }

    public List<EstablecimientoResponseDTO> listaEstablecimientoConMesas() {
        return repo.findEstablecimientoConMesas().stream()
                .map(EstablecimientoMapper::aEntidadEstablecimientoResponseDTO)
                .toList();
    }

    public EstablecimientoMesasDTO mesasResumen(Integer idEstablecimiento){
        List<Mesa> listado = mesaRepo.findByEstablecimiento_IdEstablecimiento(idEstablecimiento);
        EstablecimientoResponseDTO temp = buscarPorId(idEstablecimiento);
        if (listado.isEmpty()) {
            EstablecimientoMesasDTO vacio = new EstablecimientoMesasDTO();
            vacio.setIdEstablecimiento(idEstablecimiento);
            vacio.setNombre(temp.nombre());
            vacio.setCantidadMesas(0);
            vacio.setMesaInicial(null);
            vacio.setMesaFinal(null);
            return vacio;
        }
        EstablecimientoMesasDTO crear = new EstablecimientoMesasDTO();
        crear.setIdEstablecimiento(idEstablecimiento);
        crear.setNombre(temp.nombre());
        crear.setCantidadMesas(listado.size());
        crear.setMesaInicial(listado.get(0).getNumeroMesa());
        crear.setMesaFinal(listado.get(listado.size() -1).getNumeroMesa());
        return crear;
    }
}
