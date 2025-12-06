package org.desarrollo.service;

import org.desarrollo.dto.JornadaRequestDTO;
import org.desarrollo.dto.JornadaResponseDTO;
import org.desarrollo.mapper.JornadaMapper;
import org.desarrollo.model.Jornada;
import org.desarrollo.repository.JornadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JornadaService {

    @Autowired
    private JornadaRepository repo;

    public List<JornadaResponseDTO> listarJornadas() {
        List<JornadaResponseDTO> lista = repo.findAll()
                .stream()
                .map(JornadaMapper::aResponseDTO)
                .toList();
        return lista;
    }

    public JornadaResponseDTO buscarPorId(Integer id) {
        return repo.findById(id)
                .map(JornadaMapper::aResponseDTO)
                .orElse(null);
    }

    public JornadaResponseDTO listarPorTipo(String tipo) {
        return repo.findByTipoJornadaContainingIgnoreCase(tipo)
                .map(JornadaMapper::aResponseDTO)
                .orElse(null);
    }

    public JornadaResponseDTO guardarJornada(JornadaRequestDTO dto) {
        Jornada crear = JornadaMapper.aEntidadCreacion(dto);
        Jornada nuevo = repo.save(crear);
        return JornadaMapper.aResponseDTO(nuevo);
    }

    public boolean actualizarJornada(Integer elId, JornadaRequestDTO dto) {
        return repo.findById(elId).map(j -> {
            JornadaMapper.paraActualizarJornada(dto, j);
            repo.save(j);
            return true;
        }).orElse(false);
    }
}
