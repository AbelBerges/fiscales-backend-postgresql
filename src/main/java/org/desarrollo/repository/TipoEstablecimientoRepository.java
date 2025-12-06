package org.desarrollo.repository;

import org.desarrollo.model.TipoEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoEstablecimientoRepository extends JpaRepository<TipoEstablecimiento, Integer> {

    List<TipoEstablecimiento> findByTipoContainingIgnoreCase(String tipo);
}
