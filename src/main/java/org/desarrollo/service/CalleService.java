package org.desarrollo.service;

import jakarta.persistence.EntityManager;
import org.desarrollo.dto.CalleRequestDTO;
import org.desarrollo.dto.CalleResponseDTO;
import org.desarrollo.mapper.CalleMapper;
import org.desarrollo.model.Calle;
import org.desarrollo.repository.CalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalleService {

    @Autowired
    private CalleRepository repo;
    @Autowired
    private EntityManager em;


    public List<CalleResponseDTO> listarTodasLasCalles() {
        List<CalleResponseDTO> laLista = repo.findAll().stream()
                .map(CalleMapper::aResponseDTo)
                .toList();
        return laLista;
    }

    public CalleResponseDTO buscoPorId(Integer idCalle) {
        return repo.findById(idCalle)
                .map(CalleMapper::aResponseDTo)
                .orElse(null);
    }

    public List<CalleResponseDTO> listarPorNombreCalle(String nombre) {
        List<CalleResponseDTO> listado = repo.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(CalleMapper::aResponseDTo)
                .toList();
        return listado;
    }

    public CalleResponseDTO guardoCalle(CalleRequestDTO dto) {
        Calle nueva = CalleMapper.aEntidadDeCreacion(dto);
        Calle creada = repo.save(nueva);
        return CalleMapper.aResponseDTo(creada);
    }

    public boolean actualizoCalle(Integer idCalle, CalleRequestDTO dto) {
        return repo.findById(idCalle).map(existe -> {
            CalleMapper.datosActualizacionCalle(dto, existe);
            repo.save(existe);
            return true;
        }).orElse(false);
    }
}
