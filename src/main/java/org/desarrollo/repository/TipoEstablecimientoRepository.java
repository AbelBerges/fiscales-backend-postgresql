package org.desarrollo.repository;

import org.desarrollo.dto.TipoEstablecimientoMinimoDTO;
import org.desarrollo.model.TipoEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoEstablecimientoRepository extends JpaRepository<TipoEstablecimiento, Integer> {

    List<TipoEstablecimiento> findByTipoContainingIgnoreCase(String tipo);
    @Query("""
        SELECT new org.desarrollo.dto.TipoEstablecimientoMinimoDTO(te.idTipoEstablecimiento, te.tipo)
        FROM TipoEstablecimiento te
        ORDER BY te.tipo ASC
        """)
    public List<TipoEstablecimientoMinimoDTO> tiposOptimizado();
}
