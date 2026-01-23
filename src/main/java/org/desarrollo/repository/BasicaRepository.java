package org.desarrollo.repository;

import org.desarrollo.model.Basica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasicaRepository extends JpaRepository<Basica, Integer> {
    List<Basica> findByActivaTrueOrderByNombreAsc();
}
