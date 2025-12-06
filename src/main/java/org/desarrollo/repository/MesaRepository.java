package org.desarrollo.repository;

import org.desarrollo.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    List<Mesa> findByEstablecimiento_IdEstablecimiento(Integer idEstablecimiento);
    boolean existsByNumeroMesaAndEstablecimiento_IdEstablecimiento(Integer numeroMesa, Integer idEstablecimiento);
    Optional<Mesa> findByNumeroMesa(Integer numeroMesa);
    List<Mesa> findByEstablecimiento_IdEstablecimientoIsNull();
    List<Mesa> findByNumeroMesaIn(List<Integer> numeros);
}
