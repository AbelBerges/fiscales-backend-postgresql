package org.desarrollo.repository;

import org.desarrollo.model.TipoDepartamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoDepartamentoRepository extends JpaRepository<TipoDepartamento, Integer> {

    List<TipoDepartamento> findByNombreContainingIgnoreCase(String nombre);
}
