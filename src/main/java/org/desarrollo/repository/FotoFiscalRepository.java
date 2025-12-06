package org.desarrollo.repository;

import org.desarrollo.model.Fiscal;
import org.desarrollo.model.FotoFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FotoFiscalRepository extends JpaRepository<FotoFiscal, Integer> {
    Optional<FotoFiscal> findByFiscalIdFiscal(Integer idFiscal);
}
