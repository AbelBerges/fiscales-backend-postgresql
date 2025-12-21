package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import org.desarrollo.dto.TipoEstablecimientoMinimoDTO;
import org.desarrollo.dto.TipoEstablecimientoRequestDTO;
import org.desarrollo.dto.TipoEstablecimientoResponseDTO;
import org.desarrollo.mapper.TipoEstablecimientoMapper;
import org.desarrollo.model.TipoEstablecimiento;
import org.desarrollo.repository.TipoEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEstablecimientoService {

    @Autowired
    private TipoEstablecimientoRepository repo;
    @Autowired
    private EntityManager em;

    public List<TipoEstablecimientoResponseDTO> listarTipoEstablecimiento() {
        List<TipoEstablecimientoResponseDTO> lista = repo.findAll().stream()
                .map(TipoEstablecimientoMapper::aEntidadResponseDTO)
                .toList();
        return lista;
    }

    public TipoEstablecimientoResponseDTO buscoPorIdTipoEst(Integer id) {
        return repo.findById(id)
                .map(TipoEstablecimientoMapper::aEntidadResponseDTO)
                .orElse(null);
    }

    public List<TipoEstablecimientoResponseDTO> buscarPorElTipo(String tipo) {
        List<TipoEstablecimientoResponseDTO> lista = repo.findByTipoContainingIgnoreCase(tipo)
                .stream()
                .map(TipoEstablecimientoMapper::aEntidadResponseDTO)
                .toList();
        return lista;
    }

    public List<TipoEstablecimientoMinimoDTO> busquedaOptimizada() {
        return repo.tiposOptimizado();
    }

    public TipoEstablecimientoResponseDTO guardarTipoEst(TipoEstablecimientoRequestDTO dto) {
        TipoEstablecimiento nuevo = TipoEstablecimientoMapper.aEntidadDeCreacion(dto);
        TipoEstablecimiento creado = repo.save(nuevo);
        return TipoEstablecimientoMapper.aEntidadResponseDTO(creado);
    }

    public boolean actualizarTipoEst(Integer id, TipoEstablecimientoRequestDTO dto) {
        return repo.findById(id).map(existe -> {
            TipoEstablecimientoMapper.paraActualizarTipoEstablecimiento(dto, existe);
            repo.save(existe);
            return true;
        }).orElse(false);
    }

    public boolean desactivarTipoEst(Integer id) {
        return repo.findById(id).map(existe -> {
            existe.setActivo(false);
            repo.save(existe);
            return true;
        }).orElse(false);
    }
}
