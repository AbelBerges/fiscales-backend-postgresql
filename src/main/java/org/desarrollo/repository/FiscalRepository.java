package org.desarrollo.repository;


import org.desarrollo.dto.FiscalListaDTO;
import org.desarrollo.model.Fiscal;
import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FiscalRepository extends JpaRepository<Fiscal, Integer> {

    List<Fiscal> findByApellidoFiscalContainingIgnoreCase(String apellido);
    boolean existsByMesa_IdMesa(Integer idMesa);
    List<Fiscal> findByMesa_IdMesa(Integer idMesa);
    List<Fiscal> findByTipoFiscal_IdTipoFiscal(Integer idTipofiscal);
    List<Fiscal> findByEstablecimientoAsignado_IdEstablecimiento(Integer id);
    List<Fiscal> findByEstablecimientoAsignado_IdEstablecimientoIsNull();
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimientoIsNull(Integer tipoFiscal);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimiento(Integer iDtipoFiscal, Integer IdEstableciminetoAsignado);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndMesa_IdMesaIsNull(Integer iDTipoFiscal);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesaIsNull(Integer idTipoFiscal, Integer idJornada);
    Optional<Fiscal> findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesa(Integer idTipoFiscal, Integer IdJornada, Integer idMesa);
    @Query("SELECT f.apellidoFiscal FROM Fiscal f")
    List<String> apellidos();
    @Query("""
            SELECT new org.desarrollo.dto.FiscalListaDTO(
            f.idFiscal,
            f.nombreFiscal,
            f.apellidoFiscal,
            f.edadFiscal,
            f.correoFiscal,
            f.telefono,
            tf.nombre,
            j.tipoJornada,
            ev.nombreEstablecimiento,
            ea.nombreEstablecimiento,
            c.nombre,
            d.altura,
            p.nombre,
            dep.nombre,
            m.numeroMesa,
            f.activo
            )
            FROM Fiscal f JOIN f.tipoFiscal tf
            LEFT JOIN f.jornada j
            LEFT JOIN f.establecimientoVoto ev
            LEFT JOIN f.establecimientoAsignado ea
            JOIN f.direccion d
            LEFT JOIN d.calle c
            LEFT JOIN d.tipoPiso p
            LEFT JOIN d.tipoDepartamento dep
            LEFT JOIN f.mesa m
            WHERE f.activo = true
            ORDER BY f.apellidoFiscal ASC
            """)
    List<FiscalListaDTO> todosOptimizado();
    @Query("""
            SELECT new org.desarrollo.dto.FiscalListaDTO(
            f.idFiscal,
            f.nombreFiscal,
            f.apellidoFiscal,
            f.edadFiscal,
            f.correoFiscal,
            f.telefono,
            tf.nombre,
            j.tipoJornada,
            ev.nombreEstablecimiento,
            ea.nombreEstablecimiento,
            c.nombre,
            d.altura,
            p.nombre,
            dep.nombre,
            m.numeroMesa,
            f.activo
            )
            FROM Fiscal f JOIN f.tipoFiscal tf
            LEFT JOIN f.jornada j
            LEFT JOIN f.establecimientoVoto ev
            LEFT JOIN f.establecimientoAsignado ea
            JOIN f.direccion d
            JOIN d.calle c
            LEFT JOIN d.tipoPiso p
            LEFT JOIN d.tipoDepartamento dep
            LEFT JOIN f.mesa m
            WHERE (:idTipoFiscal IS NULL OR tf.idTipoFiscal = :idTipoFiscal)
              AND (:idJornada IS NULL OR j.idJornada = :idJornada)
              AND (:activo IS NULL OR f.activo = :activo)
              AND (:apellido = '' OR f.apellidoFiscal LIKE CONCAT('%', :apellido, '%'))
            """)
    List<FiscalListaDTO> buscarOptimizado(@Param("idTipoFiscal") Integer idTipoFiscal,
                                          @Param("idJornada") Integer idJornada,
                                          @Param("activo") Boolean activo,
                                          @Param("apellido") String apellido);
    /*@Query("""
            SELECT f FROM Fiscal f WHERE (:idTipoFiscal IS NULL OR f.tipoFiscal.id = :idTipoFiscal)
            AND (:idJornada IS NULL OR f.jornada.id = :idJornada)
            AND (:activo IS NULL OR f.activo = :activo)
            AND (:apellido = '' OR f.apellidoFiscal LIKE CONCAT('%', :apellido, '%'))
            """)
    List<Fiscal> buscarConFiltros(@Param("idTipoFiscal") Integer idTipoFiscal,
                                  @Param("idJornada") Integer idJornada,
                                  @Param("activo") Boolean activo,
                                  @Param("apellido") String apellido);*/
}
