package org.desarrollo.repository;

import org.desarrollo.model.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Integer> {
    List<Establecimiento> findByNombreEstablecimientoContainingIgnoreCase(String nombre);
    boolean existsByNombreEstablecimiento(String nombreEstablecimiento);
    @Query("SELECT e FROM Establecimiento e LEFT JOIN e.mesas m WHERE m.id IS NULL")
    List<Establecimiento> findEstablecimientosSinMesas();
    @Query("SELECT e FROM Establecimiento e LEFT JOIN e.mesas m WHERE m.id IS NOT NULL")
    List<Establecimiento> findEstablecimientoConMesas();
}
