package org.desarrollo.repository;


import org.desarrollo.dto.FiscalListaDTO;
import org.desarrollo.model.Fiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FiscalRepository extends JpaRepository<Fiscal, Integer> {

    List<Fiscal> findByApellidoFiscalContainingIgnoreCaseAndBasica_IdBasica(String apellido, Integer idBasica);
    List<Fiscal> findByApellidoFiscalContainingIgnoreCase(String apellido);
    boolean existsByMesa_IdMesa(Integer idMesa);
    List<Fiscal> findByMesa_IdMesaAndBasica_IdBasica(Integer idMesa, Integer idBasica);
    List<Fiscal> findByMesa_IdMesa(Integer idMesa);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndBasica_IdBasica(Integer idTipofiscal, Integer idBasica);
    List<Fiscal> findByTipoFiscal_IdTipoFiscal(Integer idTipoFiscal);
    List<Fiscal> findByEstablecimientoAsignado_IdEstablecimientoAndBasica_IdBasica(Integer id, Integer idBasica);
    List<Fiscal> findByEstablecimientoAsignado_IdEstablecimiento(Integer id);
    List<Fiscal> findByEstablecimientoAsignado_IdEstablecimientoIsNullAndBasica_IdBasica(Integer idBasica);
    List<Fiscal> findByEstablecimientoAsignado_IdEstablecimientoIsNull();
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimientoIsNullAndBasica_IdBasica(Integer tipoFiscal, Integer idBasica);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimientoIsNull(Integer tipoFiscal);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimientoAndBasica_IdBasica(Integer iDtipoFiscal, Integer IdEstableciminetoAsignado, Integer idBasica);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndEstablecimientoAsignado_IdEstablecimiento(Integer iDtipoFiscal, Integer IdEstableciminetoAsignado);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndMesa_IdMesaIsNullAndBasica_IdBasica(Integer iDTipoFiscal, Integer idBasica);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndMesa_IdMesaIsNull(Integer iDTipoFiscal);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesaIsNullAndBasica_IdBasica(Integer idTipoFiscal, Integer idJornada, Integer idBasica);
    List<Fiscal> findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesaIsNull(Integer idTipoFiscal, Integer idJornada);
    Optional<Fiscal> findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesaAndBasica_IdBasica(Integer idTipoFiscal, Integer IdJornada, Integer idMesa, Integer idBasica);
    Optional<Fiscal> findByTipoFiscal_IdTipoFiscalAndJornada_IdJornadaAndMesa_IdMesa(Integer idTipoFiscal, Integer IdJornada, Integer idMesa);
    @Query("SELECT f.apellidoFiscal FROM Fiscal f  WHERE f.basica.idBasica = :idBasica")
    List<String> apellidos(@Param("idBasica") Integer idBasica);
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
        f.activo,
        b.idBasica,
        b.nombre
        )
        FROM Fiscal f JOIN f.tipoFiscal tf
            LEFT JOIN f.jornada j
            LEFT JOIN f.establecimientoVoto ev
            LEFT JOIN f.establecimientoAsignado ea
            JOIN f.direccion d
            LEFT JOIN d.calle c
            LEFT JOIN d.tipoPiso p
            LEFT JOIN d.tipoDepartamento dep
            JOIN f.mesa m
            JOIN m.establecimiento e
            JOIN f.basica b
        WHERE e.idEstablecimiento = :idEst
        AND b.idBasica = :idBasica
        ORDER BY f.apellidoFiscal ASC
""")
    List<FiscalListaDTO> listarFiscalesPorEstablecimiento(@Param("idEst") Integer idEst, @Param("idBasica") Integer idBasica);

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
        f.activo,
        b.idBasica,
        b.nombre
        )
        FROM Fiscal f JOIN f.tipoFiscal tf
            LEFT JOIN f.jornada j
            LEFT JOIN f.establecimientoVoto ev
            LEFT JOIN f.establecimientoAsignado ea
            JOIN f.direccion d
            LEFT JOIN d.calle c
            LEFT JOIN d.tipoPiso p
            LEFT JOIN d.tipoDepartamento dep
            JOIN f.basica b
            JOIN f.mesa m
            JOIN m.establecimiento e
        WHERE e.idEstablecimiento = :idEst
        ORDER BY f.apellidoFiscal ASC
""")
    List<FiscalListaDTO> listarFiscalesPorEstablecimiento(@Param("idEst") Integer idEst);


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
            f.activo,
            b.idBasica,
            b.nombre
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
            LEFT JOIN f.basica b
            WHERE f.activo = true
            AND f.basica.idBasica = :idBasica
            ORDER BY f.apellidoFiscal ASC
            """)
    List<FiscalListaDTO> todosOptimizado(@Param("idBasica") Integer idBasica);

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
            f.activo,
            b.idBasica,
            b.nombre
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
            LEFT JOIN f.basica b
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
            f.activo,
            b.idBasica,
            b.nombre
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
            LEFT JOIN f.basica b
            WHERE (:idTipoFiscal IS NULL OR tf.idTipoFiscal = :idTipoFiscal)
              AND (:idJornada IS NULL OR j.idJornada = :idJornada)
              AND (:activo IS NULL OR f.activo = :activo)
              AND (:apellido IS NULL OR :apellido = '' OR f.apellidoFiscal LIKE CONCAT('%', :apellido, '%'))
              AND (f.basica.idBasica = :idBasica)
            """)
    List<FiscalListaDTO> buscarOptimizado(@Param("idTipoFiscal") Integer idTipoFiscal,
                                          @Param("idJornada") Integer idJornada,
                                          @Param("activo") Boolean activo,
                                          @Param("apellido") String apellido,
                                          @Param("idBasica") Integer idBasica);

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
            f.activo,
            b.idBasica,
            b.nombre
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
            LEFT JOIN f.basica b
            WHERE (:idTipoFiscal IS NULL OR tf.idTipoFiscal = :idTipoFiscal)
              AND (:idJornada IS NULL OR j.idJornada = :idJornada)
              AND (:activo IS NULL OR f.activo = :activo)
              AND (:apellido IS NULL OR :apellido = '' OR f.apellidoFiscal LIKE CONCAT('%', :apellido, '%'))
            """)
    List<FiscalListaDTO> buscarOptimizado(@Param("idTipoFiscal") Integer idTipoFiscal,
                                          @Param("idJornada") Integer idJornada,
                                          @Param("activo") Boolean activo,
                                          @Param("apellido") String apellido);

}
