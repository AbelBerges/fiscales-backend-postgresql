package org.desarrollo.repository;

import org.desarrollo.model.Calle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalleRepository extends JpaRepository<Calle, Integer> {

    List<Calle> findByNombreContainingIgnoreCase(String nombre);
}
