package org.desarrollo.repository;;

import org.desarrollo.model.TipoPiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoPisoRepository extends JpaRepository<TipoPiso, Integer> {

    List<TipoPiso> findByNombreContainingIgnoreCase(String nombre);
}
