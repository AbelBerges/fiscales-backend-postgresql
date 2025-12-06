package org.desarrollo.repository;

import org.desarrollo.model.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JornadaRepository extends JpaRepository<Jornada, Integer> {
    Optional<Jornada> findByTipoJornadaContainingIgnoreCase(String tipoJornada);
}
