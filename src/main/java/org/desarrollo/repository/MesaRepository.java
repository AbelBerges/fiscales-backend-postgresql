package org.desarrollo.repository;

import org.desarrollo.dto.MesaListaDTO;
import org.desarrollo.dto.MesaResponseDTO;
import org.desarrollo.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    List<Mesa> findByEstablecimiento_IdEstablecimientoAndBasica_IdBasica(Integer idEstablecimiento, Integer idBasica);
    List<Mesa> findByEstablecimiento_IdEstablecimiento(Integer idEstablecimiento);
    boolean existsByNumeroMesaAndEstablecimiento_IdEstablecimiento(Integer numeroMesa, Integer idEstablecimiento);
    Optional<Mesa> findByNumeroMesaAndBasica_IdBasica(Integer numeroMesa, Integer idBasica);
    Optional<Mesa> findByNumeroMesa(Integer numeroMesa);
    List<Mesa> findByEstablecimiento_IdEstablecimientoIsNullAndBasica_IdBasica(Integer idBasica);
    List<Mesa> findByNumeroMesaInAndBasica_IdBasica(List<Integer> numeros, Integer idBasica);
    List<Mesa> findByNumeroMesaIn(List<Integer> numeros);
    @Query(value = """
    SELECT
        m.id_mesa AS idMesa,
        m.numero_mesa AS numeroMesa,
        CASE
            WHEN COUNT(
                CASE WHEN tf.nombre = 'Mesa' THEN f.id_fiscal END
            ) = 1
            AND MAX(
                CASE WHEN tf.nombre = 'Mesa' THEN j.tipo_jornada END
            ) = 'COMPLETA'
                THEN 'COMPLETA'

            WHEN COUNT(
                CASE WHEN tf.nombre = 'Mesa' THEN f.id_fiscal END
            ) = 2
            AND SUM(
                CASE WHEN tf.nombre = 'Mesa' AND j.tipo_jornada = 'MAÑANA' THEN 1 ELSE 0 END
            ) = 1
            AND SUM(
                CASE WHEN tf.nombre = 'Mesa' AND j.tipo_jornada = 'TARDE' THEN 1 ELSE 0 END
            ) = 1
                THEN 'COMPLETA'

            WHEN COUNT(
                CASE WHEN tf.nombre = 'Mesa' THEN f.id_fiscal END
            ) = 0
                THEN 'VACIA'

            ELSE 'INCOMPLETA'
        END AS estado
    FROM mesa m
    LEFT JOIN fiscal f ON f.id_mesa = m.id_mesa
    LEFT JOIN tipo_fiscal tf ON f.id_tipo_fiscal = tf.id_tipo_fiscal
    LEFT JOIN jornada j ON f.id_jornada = j.id_jornada
    WHERE m.id_establecimiento = :idEst
        AND m.id_basica = :idBasica
    GROUP BY m.id_mesa, m.numero_mesa
""", nativeQuery = true)
    List<Object[]> estadoCrudoMesas(@Param("idEst") Integer idEst, @Param("idBasica") Integer idBasica);

    @Query(value = """
    SELECT
        m.id_mesa AS idMesa,
        m.numero_mesa AS numeroMesa,
        CASE
            WHEN COUNT(
                CASE WHEN tf.nombre = 'Mesa' THEN f.id_fiscal END
            ) = 1
            AND MAX(
                CASE WHEN tf.nombre = 'Mesa' THEN j.tipo_jornada END
            ) = 'COMPLETA'
                THEN 'COMPLETA'

            WHEN COUNT(
                CASE WHEN tf.nombre = 'Mesa' THEN f.id_fiscal END
            ) = 2
            AND SUM(
                CASE WHEN tf.nombre = 'Mesa' AND j.tipo_jornada = 'MAÑANA' THEN 1 ELSE 0 END
            ) = 1
            AND SUM(
                CASE WHEN tf.nombre = 'Mesa' AND j.tipo_jornada = 'TARDE' THEN 1 ELSE 0 END
            ) = 1
                THEN 'COMPLETA'

            WHEN COUNT(
                CASE WHEN tf.nombre = 'Mesa' THEN f.id_fiscal END
            ) = 0
                THEN 'VACIA'

            ELSE 'INCOMPLETA'
        END AS estado
    FROM mesa m
    LEFT JOIN fiscal f ON f.id_mesa = m.id_mesa
    LEFT JOIN tipo_fiscal tf ON f.id_tipo_fiscal = tf.id_tipo_fiscal
    LEFT JOIN jornada j ON f.id_jornada = j.id_jornada
    WHERE m.id_establecimiento = :idEst
    GROUP BY m.id_mesa, m.numero_mesa
""", nativeQuery = true)
    List<Object[]> estadoCrudoMesas(@Param("idEst") Integer idEst);

    @Query(value = """
    SELECT 
        m.id_mesa AS idMesa,
        m.numero_mesas AS numeroMesas,
        m.id_establecimiento,
        e.nombre_establecimiento AS nombreEstablecimiento
        FROM Mesa m JOIN m.id_establecimiento e
        ORDER BY m.numero_mesa
""", nativeQuery = true)
    List<MesaResponseDTO> listaParaTabla();

    @Query(value = """
    SELECT new org.desarrollo.dto.MesaListaDTO(
        m.idMesa,
        m.numeroMesa,
        e.nombreEstablecimiento,
        f.nombreFiscal,
        f.apellidoFiscal,
        b.nombre
    )
    FROM Mesa m
    LEFT JOIN m.establecimiento e
    LEFT JOIN m.basica b
    LEFT JOIN m.fiscales f
    ORDER BY m.numeroMesa
""")
    List<MesaListaDTO> listarMesasTabla();



}
