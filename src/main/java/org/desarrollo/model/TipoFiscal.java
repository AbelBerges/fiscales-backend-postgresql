package org.desarrollo.model;

import jakarta.persistence.*;

@Table(name = "tipo_fiscal")
@Entity(name = "TipoFiscal")
public class TipoFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_fiscal")
    private Integer idTipoFiscal;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activo = true;

    public TipoFiscal() {}

    public TipoFiscal(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

    public TipoFiscal(Integer idTipoFical,String nombre, boolean activo) {
        this.idTipoFiscal = idTipoFical;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Integer getIdTipoFiscal() {
        return this.idTipoFiscal;
    }

    public void setIdTipoFiscal(Integer idTipoFical) {
        this.idTipoFiscal = idTipoFical;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return this.activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
