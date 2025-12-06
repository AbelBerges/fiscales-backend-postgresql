package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import org.desarrollo.dto.TipoDeparamentoRequestDTO;
import org.desarrollo.dto.TipoDepartamentoResponseDTO;
import org.desarrollo.mapper.TipoDepartamentoMapper;
import org.desarrollo.model.TipoDepartamento;
import org.desarrollo.repository.TipoDepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDepartamentoservice {

    @Autowired
    private TipoDepartamentoRepository repo;
    @Autowired
    private EntityManager em;

    public List<TipoDepartamentoResponseDTO> listarTipos() {
        List<TipoDepartamentoResponseDTO> lista = repo.findAll().stream()
                .map(TipoDepartamentoMapper::aEntidadResponseDTO)
                .toList();
        return lista;
    }

    public TipoDepartamentoResponseDTO guardarTD(TipoDeparamentoRequestDTO dto) {
        TipoDepartamento nuevo = TipoDepartamentoMapper.aEntidadDeCreacion(dto);
        TipoDepartamento guardar = repo.save(nuevo);
        return TipoDepartamentoMapper.aEntidadResponseDTO(guardar);
    }

    public TipoDepartamentoResponseDTO buscoPorId(Integer idTd) {
        return repo.findById(idTd)
                .map(TipoDepartamentoMapper::aEntidadResponseDTO)
                .orElse(null);
    }

    public List<TipoDepartamentoResponseDTO> listaPorNombre(String nombre) {
        List<TipoDepartamentoResponseDTO> listar = repo.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(TipoDepartamentoMapper::aEntidadResponseDTO)
                .toList();
        return listar;
    }

    public boolean actualizarTd(Integer id, TipoDeparamentoRequestDTO dto) {
        return repo.findById(id).map(existe -> {
            TipoDepartamentoMapper.paraActualizarTipoDepartamento(dto, existe);
            repo.save(existe);
            return true;
        }).orElse(false);
    }

    public boolean desactivarTd(Integer id){
        return repo.findById(id).map(existe -> {
            existe.setActivo(false);
            repo.save(existe);
            return true;
        }).orElse(false);
    }
}
