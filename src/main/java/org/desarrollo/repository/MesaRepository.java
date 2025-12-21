package org.desarrollo.repository;

import org.desarrollo.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    List<Mesa> findByEstablecimiento_IdEstablecimiento(Integer idEstablecimiento);
    boolean existsByNumeroMesaAndEstablecimiento_IdEstablecimiento(Integer numeroMesa, Integer idEstablecimiento);
    Optional<Mesa> findByNumeroMesa(Integer numeroMesa);
    List<Mesa> findByEstablecimiento_IdEstablecimientoIsNull();
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
    GROUP BY m.id_mesa, m.numero_mesa
""", nativeQuery = true)
    List<Object[]> estadoCrudoMesas(@Param("idEst") Integer idEst);
}
