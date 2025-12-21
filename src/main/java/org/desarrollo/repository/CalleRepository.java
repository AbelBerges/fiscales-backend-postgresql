package org.desarrollo.repository;

import org.desarrollo.dto.CalleMinimaDTO;
import org.desarrollo.model.Calle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalleRepository extends JpaRepository<Calle, Integer> {

    @Query("SELECT c.nombre FROM Calle c")
    List<String> buscarCallesPorSuNombre();
    @Query("SELECT c.nombre FROM Calle c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) ORDER BY c.nombre")
    List<String> buscarPorNombre(@Param("nombre") String nombre);
    Calle findByNombreContainingIgnoreCase(String nombre);
    @Query("""
            SELECT new org.desarrollo.dto.CalleMinimaDTO(c.idCalle, c.nombre)
            FROM Calle c
            ORDER BY c.nombre ASC
            """)
    List<CalleMinimaDTO> todasLasCallesMinimaDTO();
}
