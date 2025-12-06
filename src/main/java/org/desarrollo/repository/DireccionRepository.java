package org.desarrollo.repository;

import org.desarrollo.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    List<Direccion> findByCalle_idCalle(Integer idCalle);
}
