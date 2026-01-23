package org.desarrollo.service;

import jakarta.persistence.EntityNotFoundException;
import org.desarrollo.model.Basica;
import org.desarrollo.repository.BasicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class BasicaServicio {

    @Autowired
    private BasicaRepository repoBasica;

    public Basica buscarPorId(Integer idBasica) {
        return repoBasica.findById(idBasica).orElseThrow(() ->
                new EntityNotFoundException("No se ha encontrado la básica a la que pertenece el usuario"));
    }

    public List<Basica> todasLasBasicas() throws AccessDeniedException {
        return repoBasica.findByActivaTrueOrderByNombreAsc();
        /*if (ContextoUsuario.esAdminGlobal()) {
            return repo.findByActivaTrueOrderByNombreAsc();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No tienen permisos para acceder a las básicas");*/
    }
}
