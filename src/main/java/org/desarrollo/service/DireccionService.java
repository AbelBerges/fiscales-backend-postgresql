package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import org.desarrollo.dto.DireccionRequestDTO;
import org.desarrollo.dto.DireccionResponseDTO;
import org.desarrollo.mapper.DireccionMapper;
import org.desarrollo.model.Direccion;
import org.desarrollo.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository repo;
    @Autowired
    private EntityManager em;


    public List<DireccionResponseDTO> listarDirecciones() {
        List<DireccionResponseDTO> laLista = repo.findAll()
                .stream()
                .map(DireccionMapper::aEntidadResponseDTO)
                .toList();
        return laLista;
    }

    public DireccionResponseDTO buscoDirPorId(Integer id) {
        return repo.findById(id)
                .map(DireccionMapper::aEntidadResponseDTO)
                .orElse(null);
    }

    public List<DireccionResponseDTO> listaPorCalle(Integer idCalle) {
        List<DireccionResponseDTO> laLista = repo.findByCalle_idCalle(idCalle)
                .stream()
                .map(DireccionMapper::aEntidadResponseDTO).toList();
        return laLista;
    }

    public DireccionResponseDTO guardarDireccion(DireccionRequestDTO dto) {
        Direccion nueva = DireccionMapper.aEntidadCreacion(dto, em);
        Direccion creada = repo.save(nueva);
        return DireccionMapper.aEntidadResponseDTO(creada);
    }

    public boolean actualizoDireccion(Integer id, DireccionRequestDTO dto) {
        return repo.findById(id).map(existe -> {
            DireccionMapper.paraActualizacionDireccion(dto, existe, em);
            repo.save(existe);
            return true;
        }).orElse(false);
    }




}
