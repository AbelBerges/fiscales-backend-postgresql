package org.desarrollo.repository;

import org.desarrollo.dto.EstablecimientoListaDTO;
import org.desarrollo.model.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Integer> {
    List<Establecimiento> findByNombreEstablecimientoContainingIgnoreCase(String nombre);
    boolean existsByNombreEstablecimiento(String nombreEstablecimiento);
    @Query("SELECT e FROM Establecimiento e LEFT JOIN e.mesas m WHERE m.id IS NULL")
    List<Establecimiento> findEstablecimientosSinMesas();
    @Query("SELECT e FROM Establecimiento e LEFT JOIN e.mesas m WHERE m.id IS NOT NULL")
    List<Establecimiento> findEstablecimientoConMesas();
    @Query("""
    SELECT new org.desarrollo.dto.EstablecimientoListaDTO(
        e.idEstablecimiento,
        e.nombreEstablecimiento,
        e.descripcion,
        COUNT(m),
        c.nombre,
        d.altura,
        p.nombre,
        dep.nombre,
        te.tipo,
        e.activo
    )
    FROM Establecimiento e
    LEFT JOIN e.mesas m
    LEFT JOIN e.direccion d
    LEFT JOIN d.calle c
    LEFT JOIN d.tipoPiso p
    LEFT JOIN d.tipoDepartamento dep
    LEFT JOIN e.tipoEstablecimiento te
    GROUP BY
        e.idEstablecimiento,
        e.nombreEstablecimiento,
        e.descripcion,
        c.nombre,
        d.altura,
        p.nombre,
        dep.nombre,
        te.tipo,
        e.activo
    ORDER BY e.nombreEstablecimiento ASC
""")
    List<EstablecimientoListaDTO> buscarEstablecimientoOptimizado();

    @Query("""
        SELECT new org.desarrollo.dto.EstablecimientoListaDTO(
                e.idEstablecimiento,
                e.nombreEstablecimiento,
                e.descripcion,
                COUNT(m),
                c.nombre,
                d.altura,
                p.nombre,
                dep.nombre,
                te.tipo,
                e.activo
        )
        FROM Establecimiento e
        LEFT JOIN e.mesas m
        LEFT JOIN e.direccion d
        LEFT JOIN d.calle c
        LEFT JOIN d.tipoPiso p
        LEFT JOIN d.tipoDepartamento dep
        LEFT JOIN e.tipoEstablecimiento te
        WHERE e.idEstablecimiento = :id
        GROUP BY e.idEstablecimiento, e.nombreEstablecimiento, e.descripcion,
                c.nombre, d.altura, p.nombre, dep.nombre, te.tipo, e.activo
        """)
    EstablecimientoListaDTO unoOptimizado(@Param("id") Integer id);

    @Query("""
        SELECT e.tipoEstablecimiento.tipo FROM Establecimiento e
        WHERE e.id = :idEstablecimiento
    """)
    String obtenerTipoEstablecimientoPorIdEstablecimiento(@Param("idEstablecimiento") Integer idEstablecimiento);

    @Query(value = """
        SELECT
            CASE
                WHEN SUM(CASE WHEN estado = 'COMPLETA' THEN 1 ELSE 0 END) = COUNT(*)
                    THEN 'COMPLETO'
                WHEN SUM(CASE WHEN estado = 'VACIA' THEN 1 ELSE 0 END) = COUNT(*)
                    THEN 'VACIO'
                ELSE 'INCOMPLETO'
            END AS estado_establecimiento
        FROM (
            SELECT
                CASE
                    WHEN COUNT(f.id_fiscal) = 1
                        AND MAX(j.tipo_jornada) = 'COMPLETA'
                        THEN 'COMPLETA'
                    WHEN COUNT(f.id_fiscal) = 2
                        AND SUM(CASE WHEN j.tipo_jornada = 'MAÑANA' THEN 1 ELSE 0 END) = 1
                        AND SUM(CASE WHEN j.tipo_jornada = 'TARDE' THEN 1 ELSE 0 END) = 1
                        THEN 'COMPLETA'
                    WHEN COUNT(f.id_fiscal) = 0
                        THEN 'VACIA'
                    ELSE 'INCOMPLETA'
                END AS estado
            FROM mesa m
            LEFT JOIN fiscal f ON f.id_mesa = m.id_mesa
            LEFT JOIN jornada J ON f.id_jornada = j.id_jornada
            WHERE m.id_establecimiento = :idEst
            GROUP BY m.id_mesa
        ) t
""", nativeQuery = true)
    String estadoEstablecimiento(@Param("idEst") Integer idEst);

    @Query(value = """
        SELECT
            e.id_establecimiento AS idEstablecimiento,
            e.nombre_establecimiento AS nombre,
            CASE
                WHEN SUM(CASE WHEN estado = 'COMPLETA' THEN 1 ELSE 0 END) = COUNT(*) THEN 'COMPLETO'
                WHEN SUM(CASE WHEN estado = 'VACIA' THEN 1 ELSE 0 END) = COUNT(*) THEN 'VACIO'
                ELSE 'INCOMPLETO'
            END AS estado
        FROM establecimiento e
        JOIN (
            SELECT
                m.id_establecimiento,
                CASE
                    WHEN COUNT(f.id_fiscal) = 1
                        AND MAX(j.tipo_jornada) = 'COMPLETA'
                        THEN 'COMPLETA'
                    WHEN COUNT(f.id_fiscal) = 2
                        AND SUM(CASE WHEN j.tipo_jornada = 'MAÑANA' THEN 1 ELSE 0 END) = 1
                        AND SUM(CASE WHEN j.tipo_jornada = 'TARDE' THEN 1 ELSE 0 END) = 1
                        THEN 'COMPLETA'
                    WHEN COUNT(f.id_fiscal) = 0
                        THEN 'VACIA'
                    ELSE 'INCOMPLETA'
                END AS estado
            FROM mesa m
            LEFT JOIN fiscal f ON f.id_mesa = m.id_mesa
            LEFT JOIN jornada j ON f.id_jornada = j.id_jornada
            GROUP BY m.id_mesa, m.id_establecimiento
        ) t ON t.id_establecimiento = e.id_establecimiento
        GROUP BY e.id_establecimiento, e.nombre_establecimiento
""", nativeQuery = true)
    List<Object[]> estadoEstablecimientos();

}
